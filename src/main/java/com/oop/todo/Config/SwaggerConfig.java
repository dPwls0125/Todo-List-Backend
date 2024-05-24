package com.oop.todo.Config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI WhatssueOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("TodoList OpenApi")
                        .description("객체지향 프로그래밍 프로젝트를 위한 TodoList API 문서입니다.")
                        .version("V2"));
    }
}
