package kr.anabada.anabadaserver.domain.change.service;

import jakarta.persistence.EntityManager;
import kr.anabada.anabadaserver.domain.change.dto.ProductStatus;
import kr.anabada.anabadaserver.domain.change.entity.MyProduct;
import kr.anabada.anabadaserver.domain.change.respository.ProductRepository;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.anabada.anabadaserver.domain.change.dto.ProductStatus.REQUESTING;
import static kr.anabada.anabadaserver.fixture.entity.ProductFixture.createProduct;
import static kr.anabada.anabadaserver.fixture.entity.UserFixture.craeteUser;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@DisplayName("내 물건 교환 신청 서비스")
class ProductChangeServiceTest {

    @Autowired
    ProductChangeService productChangeService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    EntityManager em;

    @Nested
    @DisplayName("createChangeRequest 메소드는")
    class createChangeRequest {

        @Test
        @DisplayName("모든 인자값이 정상일때 교환 신청을 생성한다.")
        void success() {
            // given
            User targetUser = craeteUser("target");
            MyProduct targetProduct = createProduct(targetUser, "targetProduct", ProductStatus.AVAILABLE);
            em.persist(targetUser);
            productRepository.save(targetProduct);

            User requester = craeteUser("requester");
            MyProduct requesterProduct1 = createProduct(requester, "requesterProduct1", ProductStatus.AVAILABLE);
            MyProduct requesterProduct2 = createProduct(requester, "requesterProduct2", ProductStatus.AVAILABLE);
            em.persist(requester);
            productRepository.saveAll(List.of(requesterProduct1, requesterProduct2));

            // when & then
            assertDoesNotThrow(() -> productChangeService.changeRequest(requester, targetProduct.getId(), List.of(requesterProduct1.getId(), requesterProduct2.getId()), "교환신청합니다~"));
            assertNotNull(requesterProduct1.getId());
            assertNotNull(requesterProduct2.getId());
        }

        @Test
        @DisplayName("변경 제안한 내 물건 중 하나라도 거래중인 물건이 있으면 교환 신청을 생성하지 않는다.")
        void fail() {
            // given
            User targetUser = craeteUser("target");
            MyProduct targetProduct = createProduct(targetUser, "targetProduct", ProductStatus.AVAILABLE);
            em.persist(targetUser);
            productRepository.save(targetProduct);

            User requester = craeteUser("requester");
            MyProduct requesterProduct1 = createProduct(requester, "requesterProduct1", ProductStatus.AVAILABLE);
            MyProduct requesterProduct2 = createProduct(requester, "requesterProduct2", REQUESTING);
            em.persist(requester);
            productRepository.saveAll(List.of(requesterProduct1, requesterProduct2));

            // when & then
            assertThrows(IllegalArgumentException.class,
                    () -> productChangeService.changeRequest(requester, targetProduct.getId(), List.of(requesterProduct1.getId(), requesterProduct2.getId()), "교환신청합니다~"),
                    "교환 신청에 사용된 나의 물건 중 잘못된 물건이 포함되어 있습니다.");
        }

        @Test
        @DisplayName("상대 물건 상태가 교환완료(CHANGE)인 경우 교환 신청을 생성하지 않는다.")
        void fail_target_product_already_changed() {
            // given
            User targetUser = craeteUser("target");
            MyProduct targetProduct = createProduct(targetUser, "targetProduct", ProductStatus.CHANGED);
            em.persist(targetUser);
            productRepository.save(targetProduct);

            User requester = craeteUser("requester");
            MyProduct requesterProduct1 = createProduct(requester, "requesterProduct1", ProductStatus.AVAILABLE);
            MyProduct requesterProduct2 = createProduct(requester, "requesterProduct2", ProductStatus.AVAILABLE);
            em.persist(requester);
            productRepository.saveAll(List.of(requesterProduct1, requesterProduct2));

            // when & then
            assertThrows(IllegalArgumentException.class,
                    () -> productChangeService.changeRequest(requester, targetProduct.getId(), List.of(requesterProduct1.getId(), requesterProduct2.getId()), "교환신청합니다~"),
                    "%d는 변경 신청 가능한 물건이 아닙니다.".formatted(targetProduct.getId()));
        }

        @Test
        @DisplayName("변경 신청이 생성되면, 내 물건 상태가 변경중(REQUESTING)으로 변경된다.")
        void change_my_product_status_requesting_when_success() {
            // given
            User targetUser = craeteUser("target");
            MyProduct targetProduct = createProduct(targetUser, "targetProduct", ProductStatus.AVAILABLE);
            em.persist(targetUser);
            productRepository.save(targetProduct);

            User requester = craeteUser("requester");
            MyProduct requesterProduct1 = createProduct(requester, "requesterProduct1", ProductStatus.AVAILABLE);
            MyProduct requesterProduct2 = createProduct(requester, "requesterProduct2", ProductStatus.AVAILABLE);
            em.persist(requester);
            productRepository.saveAll(List.of(requesterProduct1, requesterProduct2));

            // when
            productChangeService.changeRequest(requester, targetProduct.getId(), List.of(requesterProduct1.getId(), requesterProduct2.getId()), "교환신청합니다~");

            // then
            assertEquals(REQUESTING, requesterProduct1.getStatus());
            assertEquals(REQUESTING, requesterProduct2.getStatus());
        }

        @Test
        @DisplayName("교환에 신청한 물건 인자값이 없으면(NULL) 교환 신청을 생성하지 않는다.")
        void cant_input_myProduct_null() {
            // given
            User targetUser = craeteUser("target");
            MyProduct targetProduct = createProduct(targetUser, "targetProduct", ProductStatus.AVAILABLE);
            em.persist(targetUser);
            productRepository.save(targetProduct);

            User requester = craeteUser("requester");
            em.persist(requester);

            // when & then
            assertThrows(IllegalArgumentException.class,
                    () -> productChangeService.changeRequest(requester, targetProduct.getId(), null, "교환신청합니다~"),
                    "교환 신청할 물건은 최소 1개 이상이어야 합니다.");
        }

        @Test
        @DisplayName("교환에 신청한 물건 인자값이 없으면(EMPTY) 교환 신청을 생성하지 않는다.")
        void cant_input_myProduct_empty() {
            // given
            User targetUser = craeteUser("target");
            MyProduct targetProduct = createProduct(targetUser, "targetProduct", ProductStatus.AVAILABLE);
            em.persist(targetUser);
            productRepository.save(targetProduct);

            User requester = craeteUser("requester");
            em.persist(requester);

            // when & then
            assertThrows(IllegalArgumentException.class,
                    () -> productChangeService.changeRequest(requester, targetProduct.getId(), List.of(), "교환신청합니다~"),
                    "교환 신청할 물건은 최소 1개 이상이어야 합니다.");
        }

        @Test
        @DisplayName("상대 물건이 삭제된 경우엔 교환 신청을 생성하지 않는다.")
        void cant_create_about_removed_target_product() {
            // given
            User targetUser = craeteUser("target");
            MyProduct targetProduct = createProduct(targetUser, "targetProduct", ProductStatus.AVAILABLE);
            em.persist(targetUser);
            productRepository.save(targetProduct);

            User requester = craeteUser("requester");
            MyProduct requesterProduct = createProduct(requester, "requesterProduct1", ProductStatus.AVAILABLE);
            em.persist(requester);
            productRepository.save(requesterProduct);

            // when & then
            em.remove(targetProduct);
            assertThrows(IllegalArgumentException.class,
                    () -> productChangeService.changeRequest(requester, targetProduct.getId(), List.of(requesterProduct.getId()), "교환신청합니다~"),
                    "상대 물건이 존재하지 않습니다.");
        }

        @Test
        @DisplayName("내 물건이 삭제된 경우엔 교환 신청을 생성하지 않는다.")
        void cant_create_about_removed_my_product() {
            // given
            User targetUser = craeteUser("target");
            MyProduct targetProduct = createProduct(targetUser, "targetProduct", ProductStatus.AVAILABLE);
            em.persist(targetUser);
            productRepository.save(targetProduct);

            User requester = craeteUser("requester");
            MyProduct requesterProduct1 = createProduct(requester, "requesterProduct1", ProductStatus.AVAILABLE);
            MyProduct requesterProduct2 = createProduct(requester, "requesterProduct2", ProductStatus.AVAILABLE);
            em.persist(requester);
            productRepository.saveAll(List.of(requesterProduct1, requesterProduct2));

            // when & then
            em.remove(requesterProduct1);
            assertThrows(IllegalArgumentException.class,
                    () -> productChangeService.changeRequest(requester, targetProduct.getId(), List.of(requesterProduct1.getId()), "교환신청합니다~"),
                    "%d는 변경 신청 가능한 물건이 아닙니다.".formatted(requesterProduct1.getId()));
        }
    }
}