package com.oop.todo.global.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Value("${FRONT_URL}")
    private String frontUrl;
    private String createFailURI() {
        return UriComponentsBuilder
                .newInstance()
                .uri(URI.create(frontUrl + "/login"))
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();
    }
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.sendRedirect(createFailURI());
        log.info("로그인 실패: ");
    }

}
