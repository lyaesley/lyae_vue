package com.lyae.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class MVCConfiguration extends WebMvcConfigurerAdapter{

		@Autowired
		ModelAndViewInterceptor modelAndViewInterceptor;


		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(modelAndViewInterceptor);
		}


		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry.addResourceHandler("/resuorces/**").addResourceLocations("/resources/");
		}


		@Bean
	    public ViewResolver getViewResolver(){
	        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	        resolver.setPrefix("/WEB-INF/views/");
	        resolver.setSuffix(".jsp");
	        resolver.setOrder(2);
	        return resolver;
	    }
}
