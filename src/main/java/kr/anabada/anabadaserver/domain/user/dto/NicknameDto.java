package kr.anabada.anabadaserver.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NicknameDto {
    @JsonProperty("generated_nickname")
    private String generatedNickname;
}
