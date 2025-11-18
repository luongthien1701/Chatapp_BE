package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Chat Realtime API Documentation")
                        .version("1.0.0")
                        .description("API docs cho hệ thống chat realtime Spring Boot")
                        .contact(new Contact()
                                .name("Team Banana")
                                .email("support@banana.com")
                                .url("https://banana.com")));
    }
}
