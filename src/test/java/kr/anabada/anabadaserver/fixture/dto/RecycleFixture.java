package kr.anabada.anabadaserver.fixture.dto;

import kr.anabada.anabadaserver.domain.recycle.dto.request.RecyclePostRequest;

import java.util.List;
import java.util.UUID;

public class RecycleFixture {
    public static RecyclePostRequest createRecycle(){
        return RecyclePostRequest.builder()
                .title("title")
                .content("content")
                .images(List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString()))
                .build();
    }

    public static RecyclePostRequest modifyRecycle(){
        return RecyclePostRequest.builder()
                .title("modifyTitle")
                .content("modifyContent")
                .images(List.of(UUID.randomUUID().toString(), UUID.randomUUID().toString()))
                .build();
    }
}
