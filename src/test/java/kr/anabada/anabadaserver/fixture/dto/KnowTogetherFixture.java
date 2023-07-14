package kr.anabada.anabadaserver.fixture.dto;

import kr.anabada.anabadaserver.domain.save.dto.request.KnowTogetherOnlineRequest;
import kr.anabada.anabadaserver.domain.save.dto.request.KnowTogetherRequest;

import java.util.List;

public class KnowTogetherFixture {

    public static KnowTogetherRequest createKnowTogetherOnline() {
        return KnowTogetherOnlineRequest.builder()
                .title("title")
                .content("content")
                .productUrl("http://naver.com")
                .images(List.of("image1", "image2"))
                .build();
    }
}
