package kr.anabada.anabadaserver.common.service;

import kr.anabada.anabadaserver.common.dto.NaverProductResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NaverProductApiTest {
    @Autowired
    private NaverProductService naverProductService;

    @Test
    @DisplayName("네이버 상품 검색 테스트 성공")
    void searchProductByKeyword() {
        // given
        String keyword = "애플";
        // when
        NaverProductResponse result = naverProductService.searchProductByKeyword(keyword);
        // then
        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getItems()).hasSizeGreaterThan(0);

    }
}