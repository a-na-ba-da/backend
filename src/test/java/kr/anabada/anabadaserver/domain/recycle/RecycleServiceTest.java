package kr.anabada.anabadaserver.domain.recycle;

import kr.anabada.anabadaserver.domain.recycle.dto.RecycleDto;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.recycle.service.RecycleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecycleServiceTest {
    @Autowired
    private RecycleService recycleService;

    @Test
    @DisplayName("성공적인 게시물 추가")
    void addNewPostWithValidUser() {
        RecycleDto recycleDto = RecycleDto.builder()
                .title("test")
                .content("test")
                .writer(1L)
                .build();

        Recycle newPost = recycleService.addNewPost(1L, recycleDto);

        Assertions.assertNotNull(newPost.getId());
        Assertions.assertDoesNotThrow(() -> recycleService.addNewPost(1L, recycleDto));
    }

    @Test
    @DisplayName("유효하지 않은 유저가 게시물 추가")
    void addNewPostWithInvalidUser() {

    }

    @Test
    @DisplayName("좋아요 누르기")
    void likePost() {
        
    }

    @Test
    @DisplayName("중복 좋아요")
    void likeDupChk() {

    }
}
