package com.cit.poc.configuration;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.cit.poc.RestService;

@Configuration
@ApplicationPath("/api")
public class RestConfiguration extends ResourceConfig {

    public RestConfiguration() {
        register(RestService.class);
    }

}
