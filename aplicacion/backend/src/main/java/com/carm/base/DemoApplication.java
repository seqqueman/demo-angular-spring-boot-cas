package com.carm.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {
	
	@Autowired
	BuildProperties buildProperties;
	
	
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
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	
}
