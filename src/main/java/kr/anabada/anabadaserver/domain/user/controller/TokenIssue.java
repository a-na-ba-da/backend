package kr.anabada.anabadaserver.domain.user.controller;

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
    public GlobalResponse<JwtToken> tokenIssueKakao(String token) {
        return new GlobalResponse<>(oauthValidService.tokenIssueKakao(token));
    }
}
