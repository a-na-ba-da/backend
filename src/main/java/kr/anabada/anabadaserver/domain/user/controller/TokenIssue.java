package kr.anabada.anabadaserver.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.anabada.anabadaserver.domain.user.service.OauthValidService;
import kr.anabada.anabadaserver.global.auth.dto.JwtToken;
import kr.anabada.anabadaserver.global.response.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class TokenIssue {
    private final OauthValidService oauthValidService;

    @GetMapping("/kakao")
    @Operation(summary = "카카오 Oauth 토큰 -> 아나바다 토큰 변환 API", description = "카카오 sdk를 통해 발급된 token을 통해 아나바다에서 사용되는 jwt 인증 token을 발급합니다.")
    public GlobalResponse<JwtToken> tokenIssueKakao(@Schema(description = "카카오 sdk를 통해 발급된 token") String token) {
        return new GlobalResponse<>(oauthValidService.tokenIssueKakao(token));
    }
}
