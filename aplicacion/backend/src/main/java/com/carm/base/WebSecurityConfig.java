package com.carm.base;

import java.util.Collections;

import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	private SingleSignOutFilter singleSignOutFilter;
	private LogoutFilter logoutFilter;
	private CasAuthenticationProvider casAuthenticationProvider;
//	private ServiceProperties serviceProperties;
	private AjaxAwareAuthEntryPointWrapper ajaxAwareAuthEntryPointWrapper;

	@Autowired
	public WebSecurityConfig(SingleSignOutFilter singleSignOutFilter, LogoutFilter logoutFilter,
			CasAuthenticationProvider casAuthenticationProvider //, ServiceProperties serviceProperties
			, AjaxAwareAuthEntryPointWrapper ajaxAwareAuthEntryPointWrapper) {
		this.logoutFilter = logoutFilter;
		this.singleSignOutFilter = singleSignOutFilter;
//		this.serviceProperties = serviceProperties;
		this.casAuthenticationProvider = casAuthenticationProvider;
		this.ajaxAwareAuthEntryPointWrapper = ajaxAwareAuthEntryPointWrapper;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers( "/back/**", "/login").authenticated()
//          .and()
//          .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
//          .and()
//          .addFilterBefore(singleSignOutFilter, CasAuthenticationFilter.class)
//          .addFilterBefore(logoutFilter, LogoutFilter.class)
//          .csrf().ignoringAntMatchers("/exit/cas");
		logger.info("dentro security");
//		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

		http
		.csrf().disable()
		.authorizeRequests()
		.regexMatchers("/back.*")
		.authenticated()
		.anyRequest()
		.permitAll()
		.and()
		.httpBasic()
		.authenticationEntryPoint(ajaxAwareAuthEntryPointWrapper);//.authenticationEntryPoint(authenticationEntryPoint());

		http.logout().logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> {
			httpServletResponse.addHeader("Set-Cookie",
					"JSESSIONID=; Max-Age=0; Expires=Thu, 01-Jan-1970 00:00:10 GMT");
			httpServletResponse.addHeader("Set-Cookie",
					"XSRF-TOKEN=; Max-Age=0; Expires=Thu, 01-Jan-1970 00:00:10 GMT");
			httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		});

		http.addFilterBefore(this.logoutFilter, LogoutFilter.class);
		http.addFilterBefore(this.singleSignOutFilter, CasAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(casAuthenticationProvider);
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return new ProviderManager(Collections.singletonList(casAuthenticationProvider));
	}

}
