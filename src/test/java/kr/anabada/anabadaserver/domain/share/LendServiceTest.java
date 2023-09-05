package kr.anabada.anabadaserver.domain.share;

import jakarta.persistence.EntityManager;
import kr.anabada.anabadaserver.domain.share.dto.request.LendPostRequest;
import kr.anabada.anabadaserver.domain.share.entity.Lend;
import kr.anabada.anabadaserver.domain.share.repository.LendRepository;
import kr.anabada.anabadaserver.domain.share.service.LendService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import static kr.anabada.anabadaserver.fixture.dto.LendFixture.createLend;
import static kr.anabada.anabadaserver.fixture.dto.LendFixture.modifyLend;
import static kr.anabada.anabadaserver.fixture.entity.UserFixture.createUser;

@Nested
@Transactional
@SpringBootTest
@DisplayName("Share 서비스 테스트")
public class LendServiceTest {
    @Autowired
    private LendRepository lendRepository;

    @Autowired
    private LendService lendService;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("모든 파라메타가 정상이라면 게시글 작성에 성공한다.")
    void createLendPost_Success() {
        // given
        User user = createUser("test@test.com", "testUser");
        entityManager.persist(user);

        LendPostRequest lendPostRequest = createLend();

        // when
        Lend newLendPost = lendService.createNewLendPost(user, lendPostRequest);

        // then
        Lend findLendPost = lendRepository.findById(newLendPost.getId()).get();
        Assertions.assertEquals(findLendPost.getId(), newLendPost.getId());
        Assertions.assertNotNull(newLendPost.getId());
    }

    @Test
    @DisplayName("글 작성 시 이미지가 없는 경우, 작성에 실패한다.")
    void createNewLendPost_Failure() {
        // given
        User user = createUser("test@test.com", "testUser");
        entityManager.persist(user);

        LendPostRequest lendPostRequest = createLend();
        ReflectionTestUtils.setField(lendPostRequest, "images", null);

        // when & then
        Assertions.assertThrows(CustomException.class, () -> {
            lendService.createNewLendPost(user, lendPostRequest);
        }, ErrorCode.NOT_EXIST_IMAGE.getMessage());
    }

    @Test
    @DisplayName("본인이 작성한 글일 경우, 삭제할 수 있다.")
    void deleteLendPost_Success() {
        // given
        User user = createUser("test@test.com", "testUser");
        entityManager.persist(user);

        LendPostRequest lendPostRequest = createLend();
        Lend newLendPost = lendService.createNewLendPost(user, lendPostRequest);
        entityManager.persist(newLendPost);

        // when & then
        Assertions.assertDoesNotThrow(() -> {
            lendService.deleteLendPost(user, newLendPost.getId());
        });
    }

    @Test
    @DisplayName("본인이 작성한 글이 아닐 경우, 삭제할 수 없다.")
    void deleteLendPost_Failure() {
        // given
        User poster = createUser("poster@test.com", "poster");
        User notPoster = createUser("notPoster@test.com", "notPoster");
        entityManager.persist(poster);
        entityManager.persist(notPoster);

        LendPostRequest lendPostRequest = createLend();
        Lend newLendPost = lendService.createNewLendPost(poster, lendPostRequest);
        entityManager.persist(newLendPost);

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            lendService.deleteLendPost(notPoster, newLendPost.getId());
        }, "본인이 작성한 게시물만 삭제할 수 있습니다.");
    }

    @Test
    @DisplayName("본인이 작성한 글일 경우, 수정할 수 있다.")
    void modifyLendPost_Success() {
        // given
        User user = createUser("test@test.com", "testUser");
        entityManager.persist(user);

        LendPostRequest lendPostRequest = createLend();
        Lend newLendPost = lendService.createNewLendPost(user, lendPostRequest);
        entityManager.persist(newLendPost);

        // when & then
        Assertions.assertDoesNotThrow(() -> {
            lendService.modifyLendPost(user, newLendPost.getId(), modifyLend());
        });
    }


    @Test
    @DisplayName("본인이 작성한 글이 아닐 경우, 수정할 수 없다.")
    void modifyLendPost_Failure() {
        // given
        User poster = createUser("poster@test.com", "poster");
        User notPoster = createUser("notPoster@test.com", "notPoster");
        entityManager.persist(poster);
        entityManager.persist(notPoster);

        LendPostRequest lendPostRequest = createLend();
        Lend newLendPost = lendService.createNewLendPost(poster, lendPostRequest);
        entityManager.persist(newLendPost);

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            lendService.modifyLendPost(notPoster, newLendPost.getId(), modifyLend());
        }, "본인이 작성한 게시물만 수정할 수 있습니다.");
    }

    @Test
    @DisplayName("삭제되지 않은 글이라면, 조회 가능하다.")
    void getLendPost_Success() {
        // given
        User user = createUser("test@test.com", "testUser");
        entityManager.persist(user);

        LendPostRequest lendPostRequest = createLend();
        Lend newLendPost = lendService.createNewLendPost(user, lendPostRequest);
        entityManager.persist(newLendPost);

        // when & then
        Assertions.assertDoesNotThrow(() -> {
            lendService.getLend(newLendPost.getId());
        });
    }

    @Test
    @DisplayName("삭제된 글이라면, 조회가 불가능하다")
    void getLendPost_Failure() {
        // given
        User user = createUser("test@test.com", "testUser");
        entityManager.persist(user);

        LendPostRequest lendPostRequest = createLend();
        Lend newLendPost = lendService.createNewLendPost(user, lendPostRequest);
        lendService.deleteLendPost(user, newLendPost.getId());

        // when & then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            lendService.getLend(newLendPost.getId());
        }, "해당 게시물은 삭제되었습니다.");
    }


}
