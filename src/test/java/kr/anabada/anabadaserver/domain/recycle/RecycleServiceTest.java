package kr.anabada.anabadaserver.domain.recycle;

import jakarta.persistence.EntityManager;
import kr.anabada.anabadaserver.domain.recycle.dto.request.RecyclePostRequest;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.recycle.repository.RecycleRepository;
import kr.anabada.anabadaserver.domain.recycle.service.RecycleService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static kr.anabada.anabadaserver.fixture.dto.RecycleFixture.createRecycle;
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
}
