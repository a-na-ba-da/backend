package kr.anabada.anabadaserver.fixture.dto;

import kr.anabada.anabadaserver.domain.share.dto.request.LendPostRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class LendFixture {
    public static LendPostRequest createLend() {
        return LendPostRequest.builder()
                .title("title")
                .content("content")
                .start(LocalDate.now())
                .end(LocalDate.now())
                .pricePerDay(3000L)
                .lat(37.123456)
                .lng(127.123456)
                .images(List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString()))
                .build();
    }

    public static LendPostRequest modifyLend() {
        return LendPostRequest.builder()
                .title("modifyTitle")
                .content("modifyContent")
                .start(LocalDate.now())
                .end(LocalDate.now())
                .pricePerDay(4000L)
                .lat(12.123456)
                .lng(123.123456)
                .images(List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString()))
                .build();
    }
}
