package kr.anabada.anabadaserver.domain.save.service;

import jakarta.persistence.EntityManager;
import kr.anabada.anabadaserver.common.service.ImageService;
import kr.anabada.anabadaserver.domain.save.dto.request.KnowTogetherOfflineRequest;
import kr.anabada.anabadaserver.domain.save.dto.request.KnowTogetherOnlineRequest;
import kr.anabada.anabadaserver.domain.save.dto.request.KnowTogetherRequest;
import kr.anabada.anabadaserver.domain.save.repository.SaveRepository;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@DisplayName("아껴쓰기 서비스 - 같이 알아요")
class KnowTogetherServiceTest {

    @MockBean
    private ImageService imageService;

    @Autowired
    private KnowTogetherService knowTogetherService;

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
    @DisplayName("같이 알아요 CASE 1 : 온라인 구매 꿀팁 관련")
    class knowTogetherCase1 {
        static KnowTogetherRequest createKnowTogetherOnline() {
            return KnowTogetherOnlineRequest.builder()
                    .build();
        }

        @Test
        @DisplayName("모든 인자값이 정상이면 작성할 수 있다.")
        void createNewBuyTogetherPost_Success() {
            // given

            // when

            // then
        }
    }


    @Nested
    @DisplayName("같이 알아요 CASE 2 : 오프라인 구매 꿀팁 관련")
    class KnowTogetherCase2 {

        static KnowTogetherRequest createKnowTogetherOffline() {
            return KnowTogetherOfflineRequest.builder()
                    .build();
        }

        @Test
        @DisplayName("모든 인자값이 정상이면 작성할 수 있다.")
        void success() {
            // given

            // when

            // then
        }

    }


    @Nested
    @DisplayName("공통 항목 테스트")
    class Integration {

    }
}
