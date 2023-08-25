package kr.anabada.anabadaserver.domain.change.service;

import jakarta.persistence.EntityManager;
import kr.anabada.anabadaserver.domain.change.dto.ChangeRequestStatus;
import kr.anabada.anabadaserver.domain.change.dto.ProductStatus;
import kr.anabada.anabadaserver.domain.change.entity.ChangeRequest;
import kr.anabada.anabadaserver.domain.change.entity.MyProduct;
import kr.anabada.anabadaserver.domain.change.respository.ProductRepository;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.anabada.anabadaserver.domain.change.dto.ProductStatus.*;
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
    @DisplayName("rejectChangeRequest 메소드는")
    class rejectChangeRequest {

        @Test
        @DisplayName("모든 인자값이 정상일때 교환 신청이 정상적으로 거절된다.")
        void success() {
            // given
            User requester = craeteUser("requester");
            User requestee = craeteUser("requestee");
            em.persist(requester);
            em.persist(requestee);

            MyProduct requesterProduct = createProduct(requester, "requesterProduct", AVAILABLE);
            MyProduct requesteeProduct = createProduct(requestee, "requesteeProduct", AVAILABLE);
            em.persist(requesterProduct);
            em.persist(requesteeProduct);

            productChangeService.changeRequest(requester, requesteeProduct.getId(), List.of(requesterProduct.getId()), "교환신청합니다~");
            Long requestId = productChangeService.getAllChangeRequest(requestee).getRequestingForMeList().get(0).getId();

            // when
            productChangeService.rejectChangeRequest(requestee, requesterProduct.getId(), "거절합니다~");

            // then
            assertEquals(AVAILABLE, requesterProduct.getStatus());
            assertEquals(AVAILABLE, requesteeProduct.getStatus());
            assertEquals(ChangeRequestStatus.REJECTED, em.find(ChangeRequest.class, requestId).getStatus());
        }

        @Test
        @DisplayName("requestee 가 아닌 다른 사용자가 교환을 거절하면 예외가 발생한다.")
        void can_reject_only_requestee() {
            // given
            User requester = craeteUser("requester");
            User requestee = craeteUser("requestee");
            User another = craeteUser("another");
            em.persist(requester);
            em.persist(requestee);
            em.persist(another);

            MyProduct requesterProduct = createProduct(requester, "requesterProduct", AVAILABLE);
            MyProduct requesteeProduct = createProduct(requestee, "requesteeProduct", AVAILABLE);
            em.persist(requesterProduct);
            em.persist(requesteeProduct);

            productChangeService.changeRequest(requester, requesteeProduct.getId(), List.of(requesterProduct.getId()), "교환신청합니다~");
            Long requestId = productChangeService.getAllChangeRequest(requestee).getRequestingForMeList().get(0).getId();

            // when & then
            Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> productChangeService.rejectChangeRequest(another, requesterProduct.getId(), "거절합니다~"),
                    "본인에게 온 교환 신청만 거절할 수 있습니다."
            );
        }
    }

    @Nested
    @DisplayName("acceptChangeRequest 메소드는")
    class acceptChangeRequest {

        @Test
        @DisplayName("모든 인자값이 정상일때 교환 신청이 정상적으로 수행된다.")
        void success() {
            // given
            User requester = craeteUser("requester");
            User requestee = craeteUser("requestee");
            em.persist(requester);
            em.persist(requestee);

            MyProduct requesterProduct = createProduct(requester, "requesterProduct", AVAILABLE);
            MyProduct requesteeProduct = createProduct(requestee, "requesteeProduct", AVAILABLE);
            em.persist(requesterProduct);
            em.persist(requesteeProduct);

            productChangeService.changeRequest(requester, requesteeProduct.getId(), List.of(requesterProduct.getId()), "교환신청합니다~");
            Long requestId = productChangeService.getAllChangeRequest(requestee).getRequestingForMeList().get(0).getId();

            // when
            productChangeService.acceptChangeRequest(requestee, requesterProduct.getId());

            // then
            assertEquals(ProductStatus.CHANGED, requesterProduct.getStatus());
            assertEquals(ProductStatus.CHANGED, requesteeProduct.getStatus());
            assertEquals(ChangeRequestStatus.ACCEPTED, em.find(ChangeRequest.class, requestId).getStatus());
        }

        @Test
        @DisplayName("requestee 가 아닌 다른 사용자가 교환을 수락하면 예외가 발생한다.")
        void can_accept_change_only_requestee() {
            // given
            User requester = craeteUser("requester");
            User requestee = craeteUser("requestee");
            User another = craeteUser("another");
            em.persist(requester);
            em.persist(requestee);
            em.persist(another);

            MyProduct requesterProduct = createProduct(requester, "requesterProduct", AVAILABLE);
            MyProduct requesteeProduct = createProduct(requestee, "requesteeProduct", AVAILABLE);
            em.persist(requesterProduct);
            em.persist(requesteeProduct);

            productChangeService.changeRequest(requester, requesteeProduct.getId(), List.of(requesterProduct.getId()), "교환신청합니다~");
            Long requestId = productChangeService.getAllChangeRequest(requestee).getRequestingForMeList().get(0).getId();

            // when & then
            Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> productChangeService.acceptChangeRequest(another, requesterProduct.getId()),
                    "본인에게 온 교환 신청만 수락할 수 있습니다."
            );
        }

        @Test
        @DisplayName("양측 물건중 하나라도 교환중인 상태가 아니면 교환을 수락할 수 없다.")
        void can_accept_all_product_status_ok() {
            // given
            User requester = craeteUser("requester");
            User requestee = craeteUser("requestee");
            em.persist(requester);
            em.persist(requestee);

            MyProduct requesterProduct = createProduct(requester, "requesterProduct", AVAILABLE);
            MyProduct requesteeProduct = createProduct(requestee, "requesteeProduct", AVAILABLE);
            em.persist(requesterProduct);
            em.persist(requesteeProduct);

            productChangeService.changeRequest(requester, requesteeProduct.getId(), List.of(requesterProduct.getId()), "교환신청합니다~");
            Long requestId = productChangeService.getAllChangeRequest(requestee).getRequestingForMeList().get(0).getId();

            requesterProduct.setStatus(CHANGED);

            // when & then
            Assertions.assertThrows(
                    IllegalArgumentException.class,
                    () -> productChangeService.acceptChangeRequest(requester, requesterProduct.getId()),
                    "양측 물건중 이미 교환 완료된 물건이 존재합니다."
            );
        }

        @Test
        @DisplayName("한번 거절한 교환 신청은 다시 수락할 수 없다.")
        void test() {
            // given

            // when

            // then
        }
    }

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