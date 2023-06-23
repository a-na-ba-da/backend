package kr.anabada.anabadaserver.common.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.anabada.anabadaserver.common.dto.NaverProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
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
            log.info("네이버 상품 검색 API 호출 실패");
            log.info(e.getMessage());
        }
        return null;
    }

    // 네이버 상품 상세 페이지에 접속해서, 상품 제목과 가격을 가져온다.
    public ProductParseResult parseNaverProduct(long productId) {
        String url = "https://search.shopping.naver.com/product/" + productId;
        try {
            URL urlObj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setConnectTimeout(10000);
            con.setReadTimeout(10000);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                log.info("네이버 상품 상세 페이지 접속 실패");
                log.info("responseCode: " + responseCode);
                return null;
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine.strip());
            }
            br.close();

            String title = parseTitle(response.toString());
            int price = parsePrice(response.toString());

            return new ProductParseResult(title, price);

        } catch (Exception e) {
            throw new RuntimeException("네이버 상품 상세 페이지 파싱 실패");
        }
    }

    private int parsePrice(String html) {
        // after class="product_result_text_
        // inside 최저, 원
        try {
            String[] split = html.split("product_result_text_");
            String[] split2 = split[1].split("최저");
            String[] split3 = split2[1].split("원");

            String price = split3[0].replaceAll(",", "")
                    .replaceAll("<!--", "")
                    .replaceAll("-->", "")
                    .replaceAll(" ", "");

            return Integer.parseInt(price);
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("네이버 상품 상세 페이지 파싱 실패");
        }
    }

    private String parseTitle(String html) {
        // after class="product_bridge_product
        // inside <strong> </strong>
        try {
            String[] split = html.split("product_bridge_product");
            String[] split2 = split[1].split("<strong>");
            String[] split3 = split2[1].split("</strong>");

            return split3[0];
        } catch (IndexOutOfBoundsException e) {
            throw new RuntimeException("네이버 상품 상세 페이지 파싱 실패");
        }
    }

    public record ProductParseResult(
            String productName,
            int price
    ) {
    }
}
