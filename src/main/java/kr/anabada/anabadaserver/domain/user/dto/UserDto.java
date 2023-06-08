package kr.anabada.anabadaserver.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private long id;
    private String nickname;
    private String email;
    private Boolean activated;
}
