package kr.anabada.anabadaserver.global.auth;

import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.domain.user.repository.UserRepository;
import kr.anabada.anabadaserver.domain.user.service.NicknameService;
import kr.anabada.anabadaserver.global.auth.dto.OAuth2Attribute;
import kr.anabada.anabadaserver.global.auth.dto.SecurityRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final NicknameService nicknameService;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId(); // OAuth 서비스 이름(ex. kakao, naver, google)
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // OAuth 로그인 시 키(pk)가 되는 값
        Map<String, Object> attributes = oAuth2User.getAttributes(); // OAuth 서비스의 유저 정보들

        OAuth2Attribute oAuth2Attribute = OAuth2Attribute.of(registrationId, attributes);
        User user = null;
        try {
            user = userRepository.findByEmail(oAuth2Attribute.getEmail())
                    .orElse(oAuth2Attribute.toEntity(nicknameService.generateNickname()));
        } catch (IOException e) {
            throw new NullPointerException("자동 닉네임 생성 오류, 다시 시도해주세요.");
        }
        userRepository.save(user);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(SecurityRole.USER.toString())),
                oAuth2Attribute.convertToMap(),
                userNameAttributeName);
    }
}
