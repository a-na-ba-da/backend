package kr.anabada.anabadaserver.domain.save.service;

import jakarta.persistence.EntityManager;
import kr.anabada.anabadaserver.domain.ServiceTestWithoutImageUpload;
import kr.anabada.anabadaserver.domain.save.dto.request.KnowTogetherOfflineRequest;
import kr.anabada.anabadaserver.domain.save.dto.request.KnowTogetherOnlineRequest;
import kr.anabada.anabadaserver.domain.save.dto.request.KnowTogetherRequest;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.anabada.anabadaserver.fixture.entity.UserFixture.createUser;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@DisplayName("아껴쓰기 서비스 - 같이 알아요")
class KnowTogetherServiceTest extends ServiceTestWithoutImageUpload {

    @Autowired
    private KnowTogetherService knowTogetherService;

    @Autowired
    private EntityManager em;

    @Nested
    @DisplayName("같이 알아요 CASE 1 : 온라인 구매 꿀팁 관련")
    class knowTogetherCase1 {
        static KnowTogetherRequest createKnowTogetherOnline() {
            return KnowTogetherOnlineRequest.builder()
                    .title("title")
                    .content("content")
                    .productUrl("https://naver.com")
                    .images(List.of("image1", "image2"))
                    .build();
        }

        @Test
        @DisplayName("모든 인자값이 정상이면 작성할 수 있다.")
        void createNewKnowTogetherOnline_Success() {
            // given
            User user = createUser("email@email.com", "nickname");
            em.persist(user);

            KnowTogetherRequest request = createKnowTogetherOnline();

            // when
            Save result = knowTogetherService.createNewKnowTogetherPost(user, request);

            // then
            assertAll(
                    () -> assertNotNull(result.getId()),
                    () -> assertEquals(request.getTitle(), result.getTitle()),
                    () -> assertEquals(request.getContent(), result.getContent()),
                    () -> assertEquals(request.getProductUrl(), result.getProductUrl())
            );
        }

        @Test
        @DisplayName("상품 판매 주소가 NULL 이면 작성에 실패한다.")
        void please_input_product_url() {
            // given
            User user = createUser("email", "nickname");
            em.persist(user);

            KnowTogetherRequest request = createKnowTogetherOnline();
            ReflectionTestUtils.setField(request, "productUrl", null);

            // when & then
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                knowTogetherService.createNewKnowTogetherPost(user, request);
            }, "온라인 구매인 경우, 상품 주소를 입력해주세요.");
        }

        @Test
        @DisplayName("접속할 수 없는 상품 주소를 입력하면 작성에 실패한다.")
        void please_input_connectable_url() {
            // given
            User user = createUser("email", "nickname");
            em.persist(user);

            KnowTogetherRequest request = createKnowTogetherOnline();
            ReflectionTestUtils.setField(request, "productUrl", "http://WannabeJjangJjangHakerIntheWorld.com/wowowowowowow");

            // when & then
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                knowTogetherService.createNewKnowTogetherPost(user, request);
            }, "온라인 구매인 경우, 상품 주소를 입력해주세요.");
        }
    }

    @Nested
    @DisplayName("같이 알아요 CASE 2 : 오프라인 구매 꿀팁 관련")
    class KnowTogetherCase2 {

        static KnowTogetherOfflineRequest createKnowTogetherOffline() {
            return KnowTogetherOfflineRequest.builder()
                    .title("title")
                    .content("content")
                    .buyPlaceLat(90.321)
                    .buyPlaceLng(124.123)
                    .build();
        }

        @Test
        @DisplayName("모든 인자값이 정상이면 작성할 수 있다.")
        void success() {
            // given
            User user = createUser("email@email.com", "nickname");
            em.persist(user);

            KnowTogetherOfflineRequest request = createKnowTogetherOffline();

            // when
            Save result = knowTogetherService.createNewKnowTogetherPost(user, request);

            // then
            assertAll(
                    () -> assertNotNull(result.getId()),
                    () -> assertEquals(request.getTitle(), result.getTitle()),
                    () -> assertEquals(request.getContent(), result.getContent()),
                    () -> assertEquals(request.getBuyPlaceLat(), result.getPlaceLat()),
                    () -> assertEquals(request.getBuyPlaceLng(), result.getPlaceLng())
            );
        }

        @Test
        @DisplayName("구매 장소의 위도가 NULL 이면 작성에 실패한다.")
        void fail_if_buy_place_lat_is_null() {
            // given
            User user = createUser("email", "nickname");
            em.persist(user);

            KnowTogetherRequest request = createKnowTogetherOffline();
            ReflectionTestUtils.setField(request, "buyPlaceLat", null);

            // when & then
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                knowTogetherService.createNewKnowTogetherPost(user, request);
            }, "오프라인 구매인 경우, 구매 장소의 위도와 경도를 입력해주세요.");
        }

        @Test
        @DisplayName("구매 장소의 경도가 NULL 이면 작성에 실패한다.")
        void fail_if_buy_place_lng_is_null() {
            // given
            User user = createUser("email", "nickname");
            em.persist(user);

            KnowTogetherRequest request = createKnowTogetherOffline();
            ReflectionTestUtils.setField(request, "buyPlaceLng", null);

            // when & then
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                knowTogetherService.createNewKnowTogetherPost(user, request);
            }, "오프라인 구매인 경우, 구매 장소의 위도와 경도를 입력해주세요.");
        }
    }

    @Nested
    @DisplayName("공통 항목 테스트")
    class Integration {
        // 추후 작성 예정
    }
}
