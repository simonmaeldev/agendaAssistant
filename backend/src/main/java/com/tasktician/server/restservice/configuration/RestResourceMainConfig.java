package com.tasktician.server.restservice.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.tasktician.server.restservice.flows.HelloWebRestService;

@Component
public class RestResourceMainConfig extends ResourceConfig {

    public RestResourceMainConfig() {
        System.out.println("Registering Rest Configuration...");

        register(HelloWebRestService.class);
        register(CORSResponseFilter.class);
    }
}
