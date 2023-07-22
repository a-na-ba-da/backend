package kr.anabada.anabadaserver.domain.recycle;

import kr.anabada.anabadaserver.domain.recycle.dto.request.RecyclePostRequestDto;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.recycle.service.RecycleService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static kr.anabada.anabadaserver.fixture.entity.UserFixture.createUser;

@Transactional
@SpringBootTest
public class RecycleServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecycleService recycleService;

    @Test
    @DisplayName("게시글 작성 성공")
    void createNewRecyclePost() {
        // given
        User user = createUser("test@test.com", "testUser");
        userRepository.save(user);

        RecyclePostRequestDto recyclePostRequestDto = RecyclePostRequestDto.builder()
                .title("title")
                .content("content")
                .build();

        // when
        Recycle newRecyclePost = recycleService.createNewRecyclePost(user, recyclePostRequestDto);

        // then
        Assertions.assertNotNull(newRecyclePost.getId());

    }
}
