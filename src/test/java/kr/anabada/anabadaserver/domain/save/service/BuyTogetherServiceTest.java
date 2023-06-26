package kr.anabada.anabadaserver.domain.save.service;

import jakarta.persistence.EntityManager;
import kr.anabada.anabadaserver.common.service.ImageService;
import kr.anabada.anabadaserver.domain.save.dto.request.BuyTogetherMeetRequest;
import kr.anabada.anabadaserver.domain.save.dto.request.BuyTogetherParcelRequest;
import kr.anabada.anabadaserver.domain.save.dto.request.BuyTogetherRequest;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import kr.anabada.anabadaserver.domain.save.repository.SaveRepository;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.exception.CustomException;
import kr.anabada.anabadaserver.global.exception.ErrorCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@Transactional
@SpringBootTest
@DisplayName("아껴쓰기 서비스")
class BuyTogetherServiceTest {

    @MockBean
    private ImageService imageService;

    @Autowired
    private BuyTogetherService buyTogetherService;

    @Autowired
    private SaveRepository saveRepository;

    @Autowired
    private EntityManager em;

    User createUser(String email, String nickname) {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .activated(true)
                .role("USER")
                .build();
    }

    @Nested
    @DisplayName("같이 사요 CASE 1 : 물건 산 이후 대면으로 전달하는 경우")
    class SavingCase1 {
        static BuyTogetherRequest createBuyTogetherMeet() {
            return BuyTogetherMeetRequest.builder()
                    .title("title")
                    .content("content")
                    .pay(10000)
                    .buyDate(LocalDate.now().plusDays(7))
                    .parcelDelivery(false)
                    .deliveryPlaceLat(37.123456)
                    .deliveryPlaceLng(127.123456)
                    .productUrl("http://naver.com")
                    .images(List.of("image1", "image2"))
                    .build();
        }

        @Test
        @DisplayName("모든 인자값이 정상이면 작성할 수 있다.")
        void createNewBuyTogetherPost_Success() {
            // given
            User user = createUser("test@test.com", "test");
            em.persist(user);

            BuyTogetherRequest request = createBuyTogetherMeet();
            doNothing().when(imageService).attach(any(Long.class), any(), any(Long.class));

            // when
            Save result = buyTogetherService.createNewBuyTogetherPost(user, request);

            // then
            Save findPost = saveRepository.findById(result.getId()).get();
            Assertions.assertEquals(findPost.getTitle(), request.getTitle());
            Assertions.assertNotNull(findPost.getId());
        }


        @DisplayName("대면 전달인데, 물건을 건네줄 위치정보가 없는 경우 작성할 수 없다.")
        void Failure1() {
            // given
            User user = createUser("test@test.com", "test");
            em.persist(user);

            BuyTogetherRequest request = createBuyTogetherMeet();
            ReflectionTestUtils.setField(request, "deliveryPlaceLat", null);
            ReflectionTestUtils.setField(request, "deliveryPlaceLng", null);

            doNothing().when(imageService).attach(any(Long.class), any(), any(Long.class));

            // when & then
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                buyTogetherService.createNewBuyTogetherPost(user, request);
            }, "만나서 물건을 전달할 인근 위치를 입력해주세요.");

        }

        @Test
        @DisplayName("이미지가 없으면 작성에 실패한다.")
        void createNewBuyTogetherPost_Failure2() {
            // given
            User user = createUser("test@test.com", "test");
            em.persist(user);

            BuyTogetherRequest request = createBuyTogetherMeet();
            ReflectionTestUtils.setField(request, "images", null);

            // when & then
            Assertions.assertThrows(CustomException.class, () -> {
                buyTogetherService.createNewBuyTogetherPost(user, request);
            }, ErrorCode.NOT_EXIST_IMAGE.getMessage());
        }
    }

    @Nested
    @DisplayName("같이 사요 CASE 2 : 물건 산 이후 택배로 전달하는 경우")
    class SavingCase2 {

        static BuyTogetherRequest createBuyTogetherParcel() {
            return BuyTogetherParcelRequest.builder()
                    .title("title")
                    .content("content")
                    .pay(10000)
                    .buyDate(LocalDate.now().plusDays(7))
                    .parcelDelivery(true)
                    .productUrl("http://naver.com")
                    .images(List.of("image1", "image2"))
                    .build();
        }

        @Test
        @DisplayName("모든 인자값이 정상이면 작성할 수 있다.")
        void success() {
            // given
            User user = createUser("test@test.com", "test");
            em.persist(user);

            BuyTogetherRequest request = createBuyTogetherParcel();
            doNothing().when(imageService).attach(any(Long.class), any(), any(Long.class));

            // when
            Save result = buyTogetherService.createNewBuyTogetherPost(user, request);

            // then
            Save findPost = saveRepository.findById(result.getId()).get();
            Assertions.assertEquals(findPost.getTitle(), request.getTitle());
            Assertions.assertNotNull(findPost.getId());
        }

        @Test
        @DisplayName("구매 예정일이 과거로 지정되어있으면, 작성할 수 없다.")
        void back_date_error() {
            // given
            User user = createUser("test@test.com", "test");
            em.persist(user);

            BuyTogetherRequest request = createBuyTogetherParcel();
            ReflectionTestUtils.setField(request, "buyDate", LocalDate.now().minusDays(7));

            doNothing().when(imageService).attach(any(Long.class), any(), any(Long.class));

            // when & then
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                buyTogetherService.createNewBuyTogetherPost(user, request);
            }, "구매 예정일은 현재보다 이후로 설정해주세요.");
        }


        @Test
        @DisplayName("구매 예정일이 3달 뒤면 작성할 수 없다.")
        void future_date_error() {
            // given
            User user = createUser("test@test.com", "test");
            em.persist(user);

            BuyTogetherRequest request = createBuyTogetherParcel();
            ReflectionTestUtils.setField(request, "buyDate", LocalDate.now().plusMonths(2).plusDays(1));

            doNothing().when(imageService).attach(any(Long.class), any(), any(Long.class));

            // when & then
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                buyTogetherService.createNewBuyTogetherPost(user, request);
            }, "구매일은 현재로부터 2달 이내로 설정해주세요.");
        }

        @Test
        @DisplayName("이미지가 없으면 작성에 실패한다.")
        void case2_no_image_fail() {
            // given
            User user = createUser("test@test.com", "test");
            em.persist(user);

            BuyTogetherRequest request = createBuyTogetherParcel();
            ReflectionTestUtils.setField(request, "images", null);

            // when & then
            Assertions.assertThrows(CustomException.class, () -> {
                buyTogetherService.createNewBuyTogetherPost(user, request);
            }, ErrorCode.NOT_EXIST_IMAGE.getMessage());
        }
    }


    @Nested
    @DisplayName("공통 항목 테스트")
    class Integration {

        @Test
        @DisplayName("본인이 작성한 게시물이면 삭제할 수 있다.")
        void can_remove_post_if_mine() {
            // given
            User user = createUser("test@test.com", "test");
            em.persist(user);

            BuyTogetherRequest request = SavingCase1.createBuyTogetherMeet();
            doNothing().when(imageService).attach(any(Long.class), any(), any(Long.class));

            Save post = buyTogetherService.createNewBuyTogetherPost(user, request);
            em.persist(post);

            // when & then
            Assertions.assertDoesNotThrow(() -> {
                buyTogetherService.removeMyPost(user, post.getId());
            });
        }

        @Test
        @DisplayName("본인이 작성한 게시물이 아니면 삭제할 수 없다.")
        void cant_remove_not_my_post() {
            // given
            User poster = createUser("poster@test.com", "poster");
            User notPoster = createUser("notposter@test.com", "notposter");
            em.persist(poster);
            em.persist(notPoster);

            BuyTogetherRequest request = SavingCase1.createBuyTogetherMeet();
            doNothing().when(imageService).attach(any(Long.class), any(), any(Long.class));

            Save post = buyTogetherService.createNewBuyTogetherPost(poster, request);
            em.persist(post);

            // when & then
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                buyTogetherService.removeMyPost(notPoster, post.getId());
            }, "본인이 작성한 게시물만 삭제할 수 있습니다.");
        }

        @Test
        @DisplayName("삭제되지 않은 게시물은 조회할 수 있다.")
        void can_find_not_removed_post() {
            // given
            User poster = createUser("test@test.com", "test");
            em.persist(poster);

            BuyTogetherRequest request = SavingCase1.createBuyTogetherMeet();
            doNothing().when(imageService).attach(any(Long.class), any(), any(Long.class));

            Save post = buyTogetherService.createNewBuyTogetherPost(poster, request);

            // when & then
            Assertions.assertDoesNotThrow(() -> {
                buyTogetherService.getPost(post.getId());
            });
        }


        @Test
        @DisplayName("삭제된 게시물은 조회할 수 없다.")
        void cant_find_removed_post() {
            // given
            User poster = createUser("test@test.com", "test");
            em.persist(poster);

            BuyTogetherRequest request = SavingCase1.createBuyTogetherMeet();
            doNothing().when(imageService).attach(any(Long.class), any(), any(Long.class));

            Save post = buyTogetherService.createNewBuyTogetherPost(poster, request);
            buyTogetherService.removeMyPost(poster, post.getId());

            // when & then
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                buyTogetherService.getPost(post.getId());
            }, "해당 게시물이 없습니다.");
        }
    }
}
