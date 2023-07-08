package kr.anabada.anabadaserver.domain.recycle;

import kr.anabada.anabadaserver.domain.recycle.dto.RecyclePostRequestDto;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.recycle.repository.RecycleRepository;
import kr.anabada.anabadaserver.domain.recycle.service.RecycleService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.domain.user.repository.UserRepository;
import kr.anabada.anabadaserver.global.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@SpringBootTest
public class RecycleServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecycleRepository recycleRepository;

    @Autowired
    private RecycleService recycleService;


    @Test
    @DisplayName("성공적인 게시물 추가")
    void addNewPostWithValidUser() {
        // given
        User user = CreateUser("user1@naver.com", "user1");
        userRepository.save(user);

        RecyclePostRequestDto recycleDto = RecyclePostRequestDto.builder()
                .title("title")
                .content("content")
                .build();

        // when
        Recycle newPost = recycleService.addNewPost(user.getId(), recycleDto);

        // then
        Assertions.assertNotNull(newPost.getId());
    }

    @Test
    @DisplayName("성공적인 게시글 삭제")
    void deletePostWithValidUser(){
        // given
        User user = CreateUser("user1@naver.com", "user1");
        userRepository.save(user);

        Recycle recycle = CreateRecycle(user);
        recycleRepository.save(recycle);

        // when & then
        recycleService.deleteMyPost(user.getId(), recycle.getId());
    }

    @Test
    @DisplayName("성공적인 게시글 수정")
    void editPostWithValidUser(){
        // given
        User user = CreateUser("user1@naver.com", "user1");
        userRepository.save(user);

        RecyclePostRequestDto recycleDto = RecyclePostRequestDto.builder()
                .title("title")
                .content("content")
                .build();

        Recycle recycle = recycleService.addNewPost(user.getId(), recycleDto);

        recycleDto = RecyclePostRequestDto.builder()
                .title("modified title")
                .content("modified content")
                .build();

        // when & then
        recycleService.editMyPost(user.getId(), recycle.getId(), recycleDto);
    }

    @Test
    @DisplayName("이미 좋아요 누른 게시물엔 다시 좋아요 누를 수 없다.")
    void CantDuplicateLike() {
        // given
        User liker = CreateUser("user1@naver.com", "user1");
        User poster = CreateUser("user2@naver.com", "user2");
        userRepository.saveAll(List.of(liker, poster));

        Recycle recycle = CreateRecycle(poster);
        recycleRepository.save(recycle);

        recycleService.likePost(liker.getId(), recycle.getId());

        // when & then
        Assertions.assertThrows(CustomException.class, () -> {
            recycleService.likePost(liker.getId(), poster.getId());
        });
    }


    private User CreateUser(String email, String nickname){
        return User.builder()
                .email(email)
                .activated(true)
                .nickname(nickname)
                .role("ROLE_USER")
                .build();
    }

    private Recycle CreateRecycle(User writer){
        return Recycle.builder()
                .title("title")
                .content("content")
                .writer(writer.getId())
                .build();
    }
}
