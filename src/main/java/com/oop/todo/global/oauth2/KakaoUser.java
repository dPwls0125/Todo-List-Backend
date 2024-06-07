package com.oop.todo.global.oauth2;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;



@Data
@Builder
@AllArgsConstructor
public class KakaoUser implements OAuth2User {

    private String  registrationId;  // kakao
    private String userId;  // DB에 저장된 id
    private String oauth2Id;  // kakaoId
    private Map<String, Object> attributes;  // kakao 정보
    private Collection<? extends GrantedAuthority> authorities;  // 권한
    @Override
    public String getName() {
        return attributes.get("id").toString();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
