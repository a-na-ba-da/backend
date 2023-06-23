package kr.anabada.anabadaserver.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String nickname;
    private Boolean activated;

    @Builder
    public UserDto(long id, String nickname, Boolean activated) {
        this.id = id;
        this.nickname = nickname;
        this.activated = activated;
    }
}
