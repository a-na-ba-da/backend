package kr.anabada.anabadaserver.common.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.anabada.anabadaserver.common.dto.NaverProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class NaverProductService {
    private final String clientId;
    private final String secretKey;

    public NaverProductService(@Value("${third-party-api.naver-product-search-api.client}") String clientId,
                               @Value("${third-party-api.naver-product-search-api.secret}") String secretKey) {
        this.clientId = clientId;
        this.secretKey = secretKey;
    }

    public NaverProductResponse searchProductByKeyword(String keyword) {

        try {
            String text = URLEncoder.encode(keyword, StandardCharsets.UTF_8);
            String option = "&display=10&exclude=used:rental:cbshop";
            String apiURL = "https://openapi.naver.com/v1/search/shop?query=" + text + option;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", secretKey);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(String.valueOf(response));

            return objectMapper.treeToValue(jsonNode, NaverProductResponse.class);

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
