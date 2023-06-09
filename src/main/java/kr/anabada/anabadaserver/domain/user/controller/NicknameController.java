package kr.anabada.anabadaserver.domain.user.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kr.anabada.anabadaserver.domain.user.dto.NicknameDto;
import kr.anabada.anabadaserver.domain.user.service.NicknameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
