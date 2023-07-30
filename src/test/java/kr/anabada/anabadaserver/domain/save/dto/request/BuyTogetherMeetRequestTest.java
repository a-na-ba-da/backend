package kr.anabada.anabadaserver.domain.save.dto.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class BuyTogetherMeetRequestTest {

    @Test
    @DisplayName("BuyTogetherMeetRequest 를 생성할때 BuyTogetherRequest(부모 객체)의 parcelDelivery 값은 false 로 설정된다.")
    void test() {
        // given
        BuyTogetherRequest dto = BuyTogetherMeetRequest.builder()
                .build();

        // when & then
        assertThat(dto.isParcelDelivery()).isFalse();
    }
}