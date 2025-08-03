package com.dam.proyectodam.test.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.dam.proyectodam.configuration.AppConfig;
import com.dam.proyectodam.configuration.MongoConfig;

/*
 * Como es la Config para test, no me interesa que escanee AppConfig y MongoConfig.
 * De esta manera puedo acceder a todo el contexto con la BD de pruebas
 */
@ComponentScan(basePackages = { "com.dam.proyectodam" },
	excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = { MongoConfig.class, AppConfig.class }) )
@Configuration
public class AppTestConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources");
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(jsonConverter());
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter jsonConverter() {
		return new MappingJackson2HttpMessageConverter();
	}
	
	/* Si quiero definir el welcome-file sin xml, deber�a crearme otro servlet de spring solo 
		para hacer la redirecci�n. Puede que me perjudique luego con todo lo del login
	*/
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//	    registry.addRedirectViewController("/", "/login.html");
//	}
	
//	@Override
//	 public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
//	   configurer.enable();
//	 }
	
	
	
//	@Bean
//	public RestAspect restAspect() {
//		return new RestAspect();
//	}

}
