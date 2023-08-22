package kr.anabada.anabadaserver.domain.recycle;

import jakarta.persistence.EntityManager;
import kr.anabada.anabadaserver.domain.recycle.dto.request.RecyclePostRequest;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.recycle.repository.RecycleRepository;
import kr.anabada.anabadaserver.domain.recycle.service.RecycleService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static kr.anabada.anabadaserver.fixture.dto.RecycleFixture.createRecycle;
import static kr.anabada.anabadaserver.fixture.dto.RecycleFixture.modifyRecycle;
import static kr.anabada.anabadaserver.fixture.entity.UserFixture.createUser;

@Transactional
@SpringBootTest
public class RecycleServiceTest {
    @Autowired
    private RecycleRepository recycleRepository;

    @Autowired
    private RecycleService recycleService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("게시글 작성 성공")
    void createNewRecyclePost_Success() {
        // given
        User user = createUser("test@test.com", "testUser");
        entityManager.persist(user);

        RecyclePostRequest recyclePostRequest = createRecycle();

        // when
        Recycle newRecyclePost = recycleService.createNewRecyclePost(user, recyclePostRequest);

        // then
        Recycle findRecyclePost = recycleRepository.findById(newRecyclePost.getId()).get();
        Assertions.assertEquals(findRecyclePost.getId(), newRecyclePost.getId());
        Assertions.assertNotNull(newRecyclePost.getId());
    }


    @Test
    @DisplayName("글 작성 시 이미지가 없는 경우, 작성 실패")
    void createNewRecyclePost_Failure(){
        // given
        User user = createUser("test@test.com", "testUser");
        entityManager.persist(user);

        RecyclePostRequest recyclePostRequest = createRecycle();
        ReflectionTestUtils.setField(recyclePostRequest, "images", null);

        // when & then
        Assertions.assertThrows(CustomException.class, () -> {
            recycleService.createNewRecyclePost(user, recyclePostRequest);
        }, ErrorCode.NOT_EXIST_IMAGE.getMessage());
    }

    @Test
    @DisplayName("본인이 작성한 글일 경우, 삭제 성공")
    void deleteRecyclePost_Success(){
        // given
        User user = createUser("test@test.com", "testUser");
        entityManager.persist(user);

        RecyclePostRequest recyclePostRequest = createRecycle();
        Recycle newRecyclePost = recycleService.createNewRecyclePost(user, recyclePostRequest);
        entityManager.persist(newRecyclePost);

        // when & then
        Assertions.assertDoesNotThrow(() -> {
            recycleService.deleteRecyclePost(user, newRecyclePost.getId());
        });
    }

    @Test
    @DisplayName("본인이 작성한 글이 아닐 경우, 삭제 실패")
    void deleteRecyclePost_Failure() {
        // given
        User poster = createUser("poster@test.com", "poster");
        User notPoster = createUser("notPoster@test.com", "notPoster");
        entityManager.persist(poster);
        entityManager.persist(notPoster);

        RecyclePostRequest recyclePostRequest = createRecycle();
        Recycle newRecyclePost = recycleService.createNewRecyclePost(poster, recyclePostRequest);
        entityManager.persist(newRecyclePost);

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            recycleService.deleteRecyclePost(notPoster, newRecyclePost.getId());
        }, "본인이 작성한 게시물만 삭제할 수 있습니다.");
    }

    @Test
    @DisplayName("본인이 작성한 글일 경우, 수정 성공")
    void modifyRecyclePost_Success() {
        // given
        User user = createUser("test@test.com", "testUser");
        entityManager.persist(user);

        RecyclePostRequest recyclePostRequest = createRecycle();
        Recycle newRecyclePost = recycleService.createNewRecyclePost(user, recyclePostRequest);
        entityManager.persist(newRecyclePost);

        // when & then
        Assertions.assertDoesNotThrow(() -> {
            recycleService.modifyRecyclePost(user, newRecyclePost.getId(), modifyRecycle());
        });
    }


    @Test
    @DisplayName("본인이 작성한 글이 아닐 경우, 수정 실패")
    void modifyRecyclePost_Failure() {
        // given
        User poster = createUser("poster@test.com", "poster");
        User notPoster = createUser("notPoster@test.com", "notPoster");
        entityManager.persist(poster);
        entityManager.persist(notPoster);

        RecyclePostRequest recyclePostRequest = createRecycle();
        Recycle newRecyclePost = recycleService.createNewRecyclePost(poster, recyclePostRequest);
        entityManager.persist(newRecyclePost);

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            recycleService.modifyRecyclePost(notPoster, newRecyclePost.getId(), modifyRecycle());
        }, "본인이 작성한 게시물만 수정할 수 있습니다.");
    }

    @Test
    @DisplayName("삭제되지 않은 글이라면, 조회 가능")
    void getRecyclePost_Success(){
        // given
        User user = createUser("test@test.com", "testUser");
        entityManager.persist(user);

        RecyclePostRequest recyclePostRequest = createRecycle();
        Recycle newRecyclePost = recycleService.createNewRecyclePost(user, recyclePostRequest);
        entityManager.persist(newRecyclePost);

        // when & then
        Assertions.assertDoesNotThrow(() -> {
            recycleService.getRecycle(newRecyclePost.getId());
        });
    }

    @Test
    @DisplayName("삭제된 글이라면, 조회 불가능")
    void getRecyclePost_Failure(){
        // given
        User user = createUser("test@test.com", "testUser");
        entityManager.persist(user);

        RecyclePostRequest recyclePostRequest = createRecycle();
        Recycle newRecyclePost = recycleService.createNewRecyclePost(user, recyclePostRequest);
        recycleService.deleteRecyclePost(user, newRecyclePost.getId());

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            recycleService.getRecycle(newRecyclePost.getId());
        }, "해당 게시물은 삭제되었습니다.");
    }
}
