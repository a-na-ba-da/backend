package kr.anabada.anabadaserver.fixture.dto;

import kr.anabada.anabadaserver.domain.save.dto.request.BuyTogetherMeetRequest;
import kr.anabada.anabadaserver.domain.save.dto.request.BuyTogetherParcelRequest;
import kr.anabada.anabadaserver.domain.save.dto.request.BuyTogetherRequest;

import java.time.LocalDate;
import java.util.List;

public class BuyTogetherFixture {
    public static BuyTogetherRequest createBuyTogetherMeet() {
        return BuyTogetherMeetRequest.builder()
                .title("title")
                .content("content")
                .pay(10000)
                .buyDate(LocalDate.now().plusDays(7))
                .deliveryPlaceLat(37.123456)
                .deliveryPlaceLng(127.123456)
                .productUrl("http://naver.com")
                .images(List.of("image1", "image2"))
                .build();
    }

    public static BuyTogetherRequest createBuyTogetherParcel() {
        return BuyTogetherParcelRequest.builder()
                .title("title")
                .content("content")
                .pay(10000)
                .buyDate(LocalDate.now().plusDays(7))
                .productUrl("http://naver.com")
                .images(List.of("image1", "image2"))
                .build();
    }
}
