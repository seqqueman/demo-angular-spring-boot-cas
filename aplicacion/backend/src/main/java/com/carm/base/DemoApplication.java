package com.carm.base;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

	@Autowired
	BuildProperties buildProperties;

//	@Value("${aplicacion.servidor}")
//	private String servidor;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DemoApplication.class);
	}

	// De esta manera seteamos el context path, util en desarrollo, para controlar
	// mejor los errores de cros origin, al levantar desde el IDE o desplegar
	// directamente
	// el war en tomcat
	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
		return factory -> factory.setContextPath("/" + buildProperties.get("applicationName"));
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	@Bean
//    public WebMvcConfigurer CORSConfigurer(){
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins(servidor)
//                        .allowedMethods("*");
//            }
//        };
//    }

	@Bean
	public WebMvcConfigurer ResourcesConfigurer() {
		return new WebMvcConfigurer() {
			/*
			 * De esta manera delegamos en el routing de angular las rutas que no pertenecen
			 * a Spring, al mismo tiempo podemos activar el cacheo de los estaticos de
			 * angular
			 */
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler("/**")
						.addResourceLocations("classpath:/public/")
						.setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
						.resourceChain(true)
						.addResolver(new PathResourceResolver() {
							@Override
							protected Resource getResource(String resourcePath, Resource location) throws IOException {
								Resource requestedResource = location.createRelative(resourcePath);
								return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
										: new ClassPathResource("/public/index.html");
							}
						}).addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
			}
		};
	}

}
