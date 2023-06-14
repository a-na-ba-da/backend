package kr.anabada.anabadaserver.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {
    private final Long id;
    private final String nickname;
    private final Boolean activated;

    @Builder
    public UserDto(long id, String nickname, Boolean activated) {
        this.id = id;
        this.nickname = nickname;
        this.activated = activated;
    }
}
