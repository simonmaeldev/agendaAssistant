package com.tasktician.server.restservice.configuration;

import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import jakarta.servlet.Servlet;

@Configuration
@Primary
public class DefaultJerseyServletConfig {
    
    @Bean
    public ServletRegistrationBean<Servlet> defaultJersey(RestResourceMainConfig serviceConfig) {
        ServletRegistrationBean<Servlet> defaultJersey = new ServletRegistrationBean<>(new ServletContainer(serviceConfig));
        defaultJersey.addUrlMappings("/backend/api/*");
        defaultJersey.setName("DefaultJersey");
        defaultJersey.setLoadOnStartup(0);

        return defaultJersey;
    }
}
