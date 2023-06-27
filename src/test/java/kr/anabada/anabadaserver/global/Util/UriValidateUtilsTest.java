package kr.anabada.anabadaserver.global.Util;

import kr.anabada.anabadaserver.AnabadaServerApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("입력 받은 URL 유효성 검증")
@SpringBootTest(classes = AnabadaServerApplication.class)
class UriValidateUtilsTest {

    @Nested
    @DisplayName("isExistUrl()는 ")
    class isNotExistUrlClass {

        @Test
        @DisplayName("존재하지 않는 도메인을 입력받으면 FALSE 를 반환한다. (ERR_NAME_NOT_RESOLVED)")
        void ERR_NAME_NOT_RESOLVED() {
            // given
            String url = "https://notexistdomain.com/wqidjqwidjqwidjqwd";

            // when & then
            Assertions.assertFalse(UriValidateUtils.isExistUrl(url));
        }

        @Test
        @DisplayName("정상 URL을 입력받으면 TRUE 를 반환한다.")
        void isNotExistUrl() {
            // given
            String url = "https://www.google.com/";

            // when & then
            Assertions.assertTrue(UriValidateUtils.isExistUrl(url));
        }

        @Test
        @DisplayName("쿠팡 상품 URL을 입력받으면 TRUE 를 반환한다.")
        void isNotExistUrlWhenCoupang() {
            // given
            String url = "https://www.coupang.com/vp/products/6396408893?itemId=13659935609&vendorItemId=80912364508&q=%EC%95%84%EC%9D%B4%ED%8C%A8%EB%93%9C&itemsCount=36&searchId=a9e36c0fa53d4ccc8f732ca3cfe7e3ed&rank=0";

            // when & then
            Assertions.assertTrue(UriValidateUtils.isExistUrl(url));
        }

    }
}