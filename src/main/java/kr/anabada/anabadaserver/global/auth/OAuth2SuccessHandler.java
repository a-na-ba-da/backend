package kr.anabada.anabadaserver.global.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.anabada.anabadaserver.global.auth.dto.JwtToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenService tokenService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        JwtToken token = tokenService.generateToken(oAuth2User.getAttribute("EMAIL"));
        log.info("토큰 발행 성공 : {}", token.getAccessToken());
        writeTokenResponse(response, token);
    }

    private void writeTokenResponse(HttpServletResponse response, JwtToken token) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        //응답 스트림에 텍스트를 기록하기 위함
        PrintWriter out = response.getWriter();
        //스트림에 텍스트를 기록
        out.println(objectMapper.writeValueAsString(token));
        out.flush();
    }
}