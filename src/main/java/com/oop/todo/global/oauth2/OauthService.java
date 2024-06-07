package com.oop.todo.global.oauth2;

import com.oop.todo.Domain.User.entity.UserEntity;
import com.oop.todo.Domain.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "OAuth2UserService")
@RequiredArgsConstructor
@Transactional
@Service
public class OauthService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    /*
    OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) 메서드는
    서드파티에 사용자 정보를 요청할 수 있는 access token 을 얻고나서 실행됩니다.
    이 때 access token과 같은 정보들이 oAuth2UserRequest 파라미터에 들어있습니다.
     */

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // accessToken을 이용해서 유저의 정보를 받아온다.
        String registerId = userRequest.getClientRegistration().getClientId();
        OAuth2User oAuth2User = super.loadUser(userRequest);
        saveOrFindUser(oAuth2User,registerId);
        return super.loadUser(userRequest);  // 세션로그인에서는 userdetail을반환하여 로그인 객체로 만들지만 jwt에서는 null로만 안두면 됨.
    }

    private void saveOrFindUser(OAuth2User oAuth2User, String registerId ){

        switch (registerId){
            case "kakao"  -> kakaoRegisterOrFindUser(oAuth2User,registerId);
//            case "google" => googleSaveOrFindUser(oAuth2User);
        }
    }

    private void kakaoRegisterOrFindUser(OAuth2User oAuth2User,String registerId){
            userRepository.findByOauthId(registerId+oAuth2User.getName())
                    .orElseGet(
                            ()-> userRepository.save(UserEntity.builder()
                                    .username(oAuth2User.getName())
                                    .email(oAuth2User.getAttribute("email"))
                                    .build()
                            ));
    }

}
