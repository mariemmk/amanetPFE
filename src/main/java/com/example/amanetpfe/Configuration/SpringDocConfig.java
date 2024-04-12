package com.example.amanetpfe.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI springShOpenAPI() {
        return new OpenAPI()
                .info(infoApi());
    }

    public Info infoApi() {
        return new Info()
                .title("amanet API documentation")
                .description("amanet API documentation")
                .contact(contactApi());
    }

    public Contact contactApi() {
        return new Contact().name("amanet development team");
    }

    @Bean
    public GroupedOpenApi userPublicApi() {
        return GroupedOpenApi.builder()
                .group("user management API")
                .pathsToMatch("/user/**")
                .pathsToExclude("**")
                .build();
    }

    @Bean
    public GroupedOpenApi authPublicApi() {
        return GroupedOpenApi.builder()
                .group("authentication management API")
                .pathsToMatch("/auth/**")
                .pathsToExclude("**")
                .build();
    }
}
