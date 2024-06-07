package com.oop.todo.global.oauth2;

import com.oop.todo.Domain.User.entity.UserEntity;
import com.oop.todo.Domain.User.repository.UserRepository;
import com.oop.todo.global.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Value("${FRONT_URL}")
    private String frontUrl;

    private final TokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public String createURI(String accessToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
//        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .uri(URI.create(frontUrl+ "/"))
                .queryParams(queryParams)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();
    }


    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        // OAuth2User로 캐스팅하여 인증된 사용자 정보를 가져온다.
        KakaoUser oAuth2User = (KakaoUser) authentication.getPrincipal();

        UserEntity user = userRepository.findById(oAuth2User.getUserId()).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        // 토큰 생성
        String accessToken = jwtTokenProvider.create(user);
//        String refreshToken = jwtTokenProvider.create(oAuth2User.getMemberId(), JwtTokenType.REFRESH_TOKEN);

        String uri = createURI(accessToken);
        response.sendRedirect(uri);
    }

}
