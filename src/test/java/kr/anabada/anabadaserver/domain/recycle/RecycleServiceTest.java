package kr.anabada.anabadaserver.domain.recycle;

import kr.anabada.anabadaserver.domain.recycle.dto.RecycleDto;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.recycle.service.RecycleService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RecycleServiceTest {
    @Autowired
    private RecycleService recycleService;

    private User user;
    private RecycleDto recycleDto;

    @BeforeEach
    void setUp(){
        user = User.builder()
                .id(1L)
                .email("test@test.com")
                .role("ROLE_USER")
                .activated(true)
                .nickname("testtest")
                .build();


        recycleDto = RecycleDto.builder()
                .id(1L)
                .title("title")
                .content("content")
                .createdAt(LocalDateTime.now())
                .build();

    }


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
        // Given
        // When & Then
        assertThrows(CustomException.class, () -> recycleService.addNewPost(0 ,recycleDto));
    }

    @Test
    @DisplayName("좋아요 누르기")
    void likePost() {
        assertThrows(CustomException.class, () -> recycleService.likePost(user.getId(), 1L));
    }

    @Test
    @DisplayName("중복 좋아요")
    void likeDupChk() {

    }
}
