package com.lifecourse.course_service.application.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl("https://api.lifecoursego.com/life");
        server.setDescription("Production server");
        Server server2 = new Server();
        server2.setUrl("http://localhost:8080/life");
        server2.setDescription("Local server");
        return new OpenAPI().servers(List.of(server,server2));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("lifecourse-backend")
                .pathsToMatch("/**")
                .build();
    }
}
