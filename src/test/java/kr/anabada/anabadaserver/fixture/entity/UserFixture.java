package kr.anabada.anabadaserver.fixture.entity;

import kr.anabada.anabadaserver.domain.user.entity.User;

public class UserFixture {
    public static User craeteUser(String nickname) {
        return User.builder()
                .nickname(nickname)
                .role("ROLE_USER")
                .activated(true)
                .email("%s@test.com".formatted(nickname))
                .build();
    }

    public static User createUser(String email, String nickname) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .activated(true)
                .role("ROLE_USER")
                .build();
    }
}
