package com.carm.base;

import java.util.Collections;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.carm.base.security.AppCasAssertionUserDetailsService;
import com.carm.base.security.AppUserDetails;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	BuildProperties buildProperties;
	

	@Value("${aplicacion.servidor}")
	private String servidor;
	
	@Value("${pase.login}")
	private String paseLogin;
	
	@Value("${pase.logout}")
	private String paseLogout;

	private Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

	@Autowired
	private AjaxAwareAuthEntryPointWrapper ajaxAwareAuthEntryPointWrapper;
	
//	@Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring(). //
//            antMatchers(HttpMethod.OPTIONS, "/**"). //
//            antMatchers("/"). //
//            antMatchers("/*.{js,html}"). //
//            antMatchers("/img/**"). //
//            antMatchers("/node_modules/**"). //
//            antMatchers("/**/*.{js,html,css}");
//    }

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.info("dentro security");
		
//		http
//		.csrf().disable()
//		.httpBasic()
//		.authenticationEntryPoint(ajaxAwareAuthEntryPointWrapper)
//		.and()
//		.authorizeRequests()
//		.antMatchers("/*", "*/**","/login/**").permitAll()
//		.regexMatchers("/back.*").hasAnyRole("ROLE_USER","ROLE_ADMIN")
//		;
		

		http
		.csrf().disable()
		.authorizeRequests()
		.regexMatchers("/back.*").hasAnyRole("USER","ADMIN")
//		.authenticated()
		.anyRequest()
		.permitAll()
		.and()
		.httpBasic()
		.authenticationEntryPoint(ajaxAwareAuthEntryPointWrapper);//.authenticationEntryPoint(authenticationEntryPoint());
		
		http.addFilterAt(this.casFilter(), CasAuthenticationFilter.class);

		http.addFilterBefore(this.logoutFilter(), LogoutFilter.class);
		http.addFilterBefore(this.singleSignOutFilter(), CasAuthenticationFilter.class);
	}
	
	@Bean
    public AppCasAssertionUserDetailsService authenticationUserDetailsService() {
        return new AppCasAssertionUserDetailsService();
    }

	
	@Bean
    public CasAuthenticationFilter casFilter() throws Exception {
        CasAuthenticationFilter filter = new CasAuthenticationFilter();
        filter.setAuthenticationManager(this.authenticationManager());
        
        return filter;
    }

    @Bean
    public ServiceProperties serviceProperties() {
        logger.info("service properties");
        ServiceProperties serviceProperties = new ServiceProperties();
//        serviceProperties.setService(paseLogin);
        serviceProperties.setService(servidor+buildProperties.get("applicationName")+"/login/cas");
        serviceProperties.setSendRenew(false);
        return serviceProperties;
    }

    @Bean
    public TicketValidator ticketValidator() {
        return new Cas30ServiceTicketValidator(paseLogin);
    }

    @Bean
    public CasAuthenticationProvider casAuthenticationProvider() {
        CasAuthenticationProvider provider = new CasAuthenticationProvider();
        provider.setServiceProperties(serviceProperties());
        provider.setTicketValidator(ticketValidator());
        provider.setAuthenticationUserDetailsService(
        		this.authenticationUserDetailsService()
//          s -> new AppUserDetails("80069088Q", AuthorityUtils.createAuthorityList("ROLE_USER"), "msg88q", "", false, false, false, true)
        );
        provider.setKey("CAS_PROVIDER_LOCALHOST_8900");
        return provider;
    }


    @Bean
    public SecurityContextLogoutHandler securityContextLogoutHandler() {
        return new SecurityContextLogoutHandler();
    }

	@Bean
	public LogoutFilter logoutFilter() {
		LogoutFilter logoutFilter = new LogoutFilter(paseLogout + servidor + buildProperties.get("applicationName"),
				securityContextLogoutHandler());
		logoutFilter.setFilterProcessesUrl("/logout/cas");
		return logoutFilter;
	}

    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
    	return new SingleSignOutFilter();
    }
	
    @Bean
    public AuthenticationEntryPoint casEntryPoint() {
		CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
		entryPoint.setLoginUrl(paseLogin);
		entryPoint.setServiceProperties(serviceProperties());
		return entryPoint;
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return new ProviderManager(Collections.singletonList(this.casAuthenticationProvider()));
	}

}
