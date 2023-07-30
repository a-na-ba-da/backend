package kr.anabada.anabadaserver.domain.save.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class BuyTogetherParcelRequestTest {

    @Test
    @DisplayName("BuyTogetherParcelRequest 를 생성할때 BuyTogetherRequest(부모 객체)의 parcelDelivery 값은 true 로 설정된다.")
    void test() {
        // given
        BuyTogetherRequest dto = BuyTogetherParcelRequest.builder()
                .build();

        // when & then
        assertThat(dto.isParcelDelivery()).isTrue();
    }
}