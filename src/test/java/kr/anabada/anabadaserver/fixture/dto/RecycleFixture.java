package kr.anabada.anabadaserver.fixture.dto;

import kr.anabada.anabadaserver.domain.recycle.dto.request.RecyclePostRequest;

import java.util.List;

public class RecycleFixture {
    public static RecyclePostRequest createRecycle(){
        return RecyclePostRequest.builder()
                .title("title")
                .content("content")
                .images(List.of("image1", "image2"))
                .build();
    }

    public static RecyclePostRequest modifyRecycle(){
        return RecyclePostRequest.builder()
                .title("modifyTitle")
                .content("modifyContent")
                .images(List.of("image3", "image4"))
                .build();
    }
}
