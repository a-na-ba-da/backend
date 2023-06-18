package kr.anabada.anabadaserver.domain.user.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import kr.anabada.anabadaserver.domain.user.dto.NicknameDto;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.domain.user.service.NicknameService;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/nickname")
public class NicknameController {
    private final NicknameService nicknameService;

    @GetMapping("")
    @ApiResponse(responseCode = "200", description = "랜덤 닉네임 생성 성공")
    public NicknameDto generateNickname() {
        String nickname;
        try {
            nickname = nicknameService.generateNickname();
        } catch (IOException e) {
            nickname = "닉네임 생성 오류";
        }
        return new NicknameDto(nickname);
    }

    @PutMapping("")
    @ApiResponse(responseCode = "204", description = "닉네임 변경 성공")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeNickname(@CurrentUser User user,
                               @NotNull(message = "닉네임을 입력해주세요") String nickname) {
        if (StringUtils.hasText(nickname)) {
            nicknameService.changeNickname(user.getId(), nickname);
        } else {
            throw new IllegalArgumentException("닉네임을 입력해주세요.");
        }
    }

}
