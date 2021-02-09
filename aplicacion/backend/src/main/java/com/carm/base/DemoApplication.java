package com.carm.base;

import java.util.Arrays;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

//@RestController
@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {
	
	@Autowired
	BuildProperties buildProperties;
	
	@Value("${aplicacion.servidor}")
	private String servidor;
	
	@Value("${pase.login}")
	private String pase;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DemoApplication.class);
	}
    
    // De esta manera seteamos el context path, util en desarrollo, para controlar 
    // mejor los errores de cros origin, al levantar desde el IDE o desplegar directamente
    // el war en tomcat
	@Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
        webServerFactoryCustomizer() {

        return factory -> factory.setContextPath("/"+buildProperties.get("applicationName"));
    }
	
	/*
	@RequestMapping("/")
	public String greet() {
		return "Hola mundo!";
	}
	*/
	private static final Logger logger = LoggerFactory.getLogger(DemoApplication.class);
	
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
    @Bean
    public CasAuthenticationFilter casAuthenticationFilter(
      AuthenticationManager authenticationManager,
      ServiceProperties serviceProperties) throws Exception {
        CasAuthenticationFilter filter = new CasAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationManager(new ProviderManager(Arrays.asList(this.casAuthenticationProvider())));
        filter.setServiceProperties(serviceProperties);
        return filter;
    }

    @Bean
    public ServiceProperties serviceProperties() {
        logger.info("service properties");
        ServiceProperties serviceProperties = new ServiceProperties();
        serviceProperties.setService(pase);
//        serviceProperties.setService("http://localhost:4200");
        serviceProperties.setSendRenew(false);
        return serviceProperties;
    }

    @Bean
    public TicketValidator ticketValidator() {
        return new Cas30ServiceTicketValidator(servidor+buildProperties.get("applicationName") +"/cas");
//        return new Cas30ServiceTicketValidator("https://pase-pru.carm.es/pase");
    }

    @Bean
    public CasAuthenticationProvider casAuthenticationProvider(
//      TicketValidator ticketValidator,
//      ServiceProperties serviceProperties
      ) {
        CasAuthenticationProvider provider = new CasAuthenticationProvider();
        provider.setServiceProperties(serviceProperties());
        provider.setTicketValidator(ticketValidator());
        provider.setUserDetailsService(
          s -> new User("miguel-angel.sequedo@getronics.com", "Miguel Angel", true, true, true, true,
          AuthorityUtils.createAuthorityList("ROLE_USER")));
        provider.setKey("CAS_PROVIDER_LOCALHOST_8900");
        return provider;
    }


    @Bean
    public SecurityContextLogoutHandler securityContextLogoutHandler() {
        return new SecurityContextLogoutHandler();
    }

    @Bean
    public LogoutFilter logoutFilter() {
        LogoutFilter logoutFilter = new LogoutFilter(servidor+buildProperties.get("applicationName") +"/logout", securityContextLogoutHandler());
        logoutFilter.setFilterProcessesUrl("/logout/cas");
        return logoutFilter;
    }

    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
//        singleSignOutFilter.setCasServerUrlPrefix("https://localhost:8443");
        singleSignOutFilter.setLogoutCallbackPath("/exit/cas");
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        return singleSignOutFilter;
    }
	
    @Bean
    public AuthenticationEntryPoint casEntryPoint() {
		CasAuthenticationEntryPoint entryPoint = new CasAuthenticationEntryPoint();
		entryPoint.setLoginUrl(servidor+buildProperties.get("applicationName") +"/login/cas");
//		entryPoint.setLoginUrl("https://pase-pru.carm.es/pase/login");
		entryPoint.setServiceProperties(serviceProperties());
		return entryPoint;
	}
}
