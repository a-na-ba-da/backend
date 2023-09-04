package kr.anabada.anabadaserver.global.auth.dto;

import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Builder
@Getter
public class OAuth2Attribute {
    private static final String CONSTANT_EMAIL = "email";
    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String provider;

    public static OAuth2Attribute of(String provider,
                                     Map<String, Object> attributes) {
        return switch (provider) {
            case "kakao" -> ofKakao(CONSTANT_EMAIL, attributes);
            default -> throw new NullPointerException("provider is null");
        };
    }

    private static OAuth2Attribute ofKakao(String attributeKey,
                                           Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        return OAuth2Attribute.builder()
                .email((String) kakaoAccount.get(CONSTANT_EMAIL))
                .attributes(kakaoAccount)
                .attributeKey(attributeKey)
                .provider("kakao")
                .build();
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put(CONSTANT_EMAIL, email);

        return map;
    }

    public User toEntity(String nickname) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .build();
    }
}
