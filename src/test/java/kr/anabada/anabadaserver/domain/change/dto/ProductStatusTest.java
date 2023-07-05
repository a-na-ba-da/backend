package kr.anabada.anabadaserver.domain.change.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("물건 상태 테스트")
class ProductStatusTest {

    @ParameterizedTest
    @DisplayName("물건 상태 반환값 테스트")
    @CsvSource({
            "CHANGED, CHANGED",
            "REQUESTING, REQUESTING",
            "AVAILABLE, AVAILABLE"})
    void testProductStatus(String status, String expected) {
        assertEquals(ProductStatus.of(status).toString(), expected);
    }
}