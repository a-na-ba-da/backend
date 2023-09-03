package kr.anabada.anabadaserver.domain.user.service;

import kr.anabada.anabadaserver.global.auth.JwtTokenService;
import kr.anabada.anabadaserver.global.auth.dto.JwtToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.google.api.client.http.HttpMethods.GET;

@Service
public class OauthValidService {
    private final JwtTokenService tokenService;
    private final String KAKAO_URL;

    public OauthValidService(JwtTokenService tokenService,
                             @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}") String KAKAO_URL) {
        this.tokenService = tokenService;
        this.KAKAO_URL = KAKAO_URL;
    }

    public JwtToken tokenIssueKakao(String token) {
        String email = getEmailFromKakaoToken(token);
        if (!StringUtils.hasText(email)) throw new RuntimeException("잘못된 OAUTH 토큰입니다.");
        return tokenService.generateToken(email);
    }

    private String getEmailFromKakaoToken(String token) {
        String header = "Bearer " + token;

        try {
            URL apiUrl = new URL(KAKAO_URL);
            HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
            conn.setRequestMethod(GET);
            conn.setRequestProperty("Authorization", header);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();

            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.printf("Kakao API 응답 : %s\n", response);

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Kakao API 요청 중 오류 발생");
        }
    }

}
