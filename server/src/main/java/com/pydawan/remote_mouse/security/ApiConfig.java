package com.pydawan.remote_mouse.security;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class ApiConfig {

    @Bean
    public OpenAPI publicApi() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Remote Mouse API")
                        .description("API for the Remote Mouse application"));
    }
}
