package com.oop.todo.Config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//http://localhost:8080/swagger-ui/index.html
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI TodoOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Whatssue OpenApi")
                        .description("소모임 출석 관리 application_ Whatssue?의 Open API입니다.")
                        .version("V2")
                        .contact(new Contact()
                                .name("whatssue")
                                .email("kimphoby0125@gmail.com")));

    }

}
