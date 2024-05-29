package com.oop.todo.Config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//http://localhost:8080/swagger-ui/index.html
@Configuration
public class SpringdocConfig {
    @Bean
    public OpenAPI WhatssueOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TodoList OpenApi")
                        .description("TodoList Open API입니다.")
                        .version("V2"));

    }

// 후에 spring security 사용할떄는  SecurityConfig 에서 뚫어주기
//            "/v3/api-docs/**",
//            "/swagger-ui/**",
//            "/swagger-resources/**",
}
