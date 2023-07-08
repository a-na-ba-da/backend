package kr.anabada.anabadaserver.domain.change.respository;

import jakarta.persistence.EntityManager;
import kr.anabada.anabadaserver.AnabadaServerApplication;
import kr.anabada.anabadaserver.domain.change.dto.ChangeRequestResponse;
import kr.anabada.anabadaserver.domain.change.dto.ChangeRequestStatus;
import kr.anabada.anabadaserver.domain.change.dto.ProductStatus;
import kr.anabada.anabadaserver.domain.change.entity.MyProduct;
import kr.anabada.anabadaserver.domain.change.service.ProductChangeService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.anabada.anabadaserver.fixture.entity.ProductFixture.createProduct;
import static kr.anabada.anabadaserver.fixture.entity.UserFixture.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@Transactional
@DisplayName("교환 요청 쿼리 (ChangeRequestRepositoryImpl)")
@SpringBootTest(classes = AnabadaServerApplication.class)
class ChangeRequestRepositoryImplTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ChangeRequestRepository changeRequestRepository;

    @Autowired
    private ProductChangeService productChangeService;

    @Nested
    @DisplayName("getAllRequestedByMe는")
    class GetAllRequestedByMe {

        @Test
        @DisplayName("내가 생성한 교환 신청 목록을 가져온다.")
        void getAllRequestedByMe() {
            // given
            User me = createUser("me@email.com", "me");
            em.persist(me);

            User another = createUser("another@email.com", "another");
            em.persist(another);

            List<MyProduct> myProductList = List.of(
                    createProduct(me, "내가 요청한 물건1", ProductStatus.AVAILABLE),
                    createProduct(me, "내가 요청한 물건2", ProductStatus.AVAILABLE),
                    createProduct(me, "내가 요청한 물건3", ProductStatus.AVAILABLE)
            );

            MyProduct targetProduct = createProduct(another, "상대의 물건", ProductStatus.AVAILABLE);
            productRepository.saveAll(List.of(targetProduct, myProductList.get(0), myProductList.get(1), myProductList.get(2)));

            List<Long> toChangeProductIds = List.of(myProductList.get(0).getId(), myProductList.get(1).getId(), myProductList.get(2).getId());
            productChangeService.changeRequest(me, targetProduct.getId(), toChangeProductIds, "교환요청 메시지");

            // when
            List<ChangeRequestResponse.ReqByMe> result = changeRequestRepository.getAllRequestedByMe(me);

            // then
            assertThat(result).hasSize(1)
                    .extracting("targetProduct.name", "message", "status")
                    .containsExactlyInAnyOrder(
                            tuple("상대의 물건", "교환요청 메시지", ChangeRequestStatus.REQUESTING)
                    );

            assertThat(result.get(0).getToChangeProducts())
                    .extracting("name")
                    .containsExactlyInAnyOrder(
                            "내가 요청한 물건1",
                            "내가 요청한 물건2",
                            "내가 요청한 물건3"
                    );
        }

        @Nested
        @DisplayName("getAllRequestingForMe는")
        class GetAllRequestingForMe {
            @Test
            @DisplayName("내가 요청 받은 교환 요청 목록을 조회한다.")
            void getAllRequestingForMe() {
                // given
                User me = createUser("me@email.com", "me");
                em.persist(me);

                User requester = createUser("requester@email.com", "requester");
                em.persist(requester);

                List<MyProduct> requesterProducts = List.of(
                        createProduct(requester, "상대의 물건1", ProductStatus.AVAILABLE),
                        createProduct(requester, "상대의 물건2", ProductStatus.AVAILABLE),
                        createProduct(requester, "상대의 물건3", ProductStatus.AVAILABLE)
                );

                MyProduct myTargetProduct = createProduct(me, "나의 물건", ProductStatus.AVAILABLE);
                productRepository.saveAll(List.of(myTargetProduct, requesterProducts.get(0), requesterProducts.get(1), requesterProducts.get(2)));

                List<Long> toChangeProductIds = List.of(requesterProducts.get(0).getId(), requesterProducts.get(1).getId(), requesterProducts.get(2).getId());
                productChangeService.changeRequest(requester, myTargetProduct.getId(), toChangeProductIds, "교환요청 메시지");

                // when
                List<ChangeRequestResponse.ReqForMe> result = changeRequestRepository.getAllRequestingForMe(me);

                // then
                assertThat(result).hasSize(1)
                        .extracting("targetProduct.name", "message", "status")
                        .containsExactlyInAnyOrder(
                                tuple("나의 물건", "교환요청 메시지", ChangeRequestStatus.REQUESTING)
                        );

                assertThat(result.get(0).getToChangeProducts())
                        .extracting("name")
                        .containsExactlyInAnyOrder(
                                "상대의 물건1",
                                "상대의 물건2",
                                "상대의 물건3"
                        );
            }
        }
    }
}