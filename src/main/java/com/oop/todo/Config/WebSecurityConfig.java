package com.oop.todo.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oop.todo.Token.JwtAuthenticationFilter;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig  {
    private static final String[] AUTH_WHITELIST = {
            // swagger
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/signUp",
            "/h2-console/**",
            "/auth/**",
            "/"
    };

    private final ObjectMapper objectMapper;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public WebSecurityConfig(ObjectMapper objectMapper, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.objectMapper = objectMapper;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }


//
//    @Bean
//    @ConditionalOnProperty(name = "spring.h2.console.enabled", havingValue = "true")
//    public WebSecurityCustomizer configureH2ConsoleEnable() {
//        return web -> web.ignoring()
//                .requestMatchers(PathRequest.toH2Console());
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .httpBasic(basic -> basic.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests -> {
                        try {
                            authorizeRequests
                                    .requestMatchers(AUTH_WHITELIST).permitAll();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });


        http.exceptionHandling(except -> {
            except.authenticationEntryPoint((request, response, e) -> {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("status", HttpServletResponse.SC_FORBIDDEN);
                data.put("message", e.getMessage());

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                objectMapper.writeValue(response.getOutputStream(), data);
            });
        });

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public PasswordEncoder passWordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @PostConstruct
    void init(){
        log.info("security config init");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final long MAX_AGE_SECS = 3600;
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        config.setMaxAge(MAX_AGE_SECS);
        source.registerCorsConfiguration("/**", config);
        return source;

    }
}
