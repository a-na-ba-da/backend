package kr.anabada.anabadaserver.domain.save.service;

import kr.anabada.anabadaserver.common.service.ImageService;
import kr.anabada.anabadaserver.domain.save.dto.BuyTogetherDto;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import kr.anabada.anabadaserver.domain.save.repository.SaveRepository;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.exception.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class BuyTogetherServiceTest {
    @Mock
    private SaveRepository saveRepository;
    @Mock
    private ImageService imageService;

    @InjectMocks
    private BuyTogetherService buyTogetherService;

    private User user;
    private BuyTogetherDto buyTogetherDto;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@test.com")
                .role("ROLE_USER")
                .activated(true)
                .nickname("testtest")
                .build();

        buyTogetherDto = BuyTogetherDto.builder()
                .title("title")
                .content("content")
                .pay(10000)
                .isOnlineDelivery(true)
                .buyPlaceLat(null)
                .buyPlaceLng(null)
                .images(Collections.singletonList("image"))
                .buyDate(LocalDate.now())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("아껴쓰기 - 같이사요 | 게시물 등록 성공 테스트")
    void createNewBuyTogetherPost_Success() {
        // Given
        long fakePostId = 1L;
        Save buyTogether = buyTogetherDto.toEntity();
        ReflectionTestUtils.setField(buyTogether, "id", fakePostId);

        given(saveRepository.save(any(Save.class))).willReturn(buyTogether);
        given(saveRepository.findById(fakePostId)).willReturn(Optional.of(buyTogether));
        doNothing().when(imageService).attach(any(Long.class), any(), any(Long.class));

        // When
        Save result = buyTogetherService.createNewBuyTogetherPost(user, buyTogetherDto);

        // Then
        Save findPost = saveRepository.findById(result.getId()).get();
        assertEquals(buyTogetherDto.getTitle(), findPost.getTitle());
    }

    @Test
    @DisplayName("아껴쓰기 - 같이사요 | 게시물 실패 테스트 | 비대면 전달인데, 위치정보가 없는 경우")
    void createNewBuyTogetherPost_Failure1() {
        // Given
        buyTogetherDto.setOnlineDelivery(false);
        buyTogetherDto.setBuyPlaceLat(null);
        buyTogetherDto.setBuyPlaceLng(null);

        // When & Then
        assertThrows(CustomException.class, () -> buyTogetherService.createNewBuyTogetherPost(user, buyTogetherDto));
    }

    @Test
    @DisplayName("아껴쓰기 - 같이사요 | 게시물 실패 테스트 | 이미지가 한장도 없을 때")
    void createNewBuyTogetherPost_Failure2() {
        // Given
        buyTogetherDto.setImages(null);

        // When & Then
        assertThrows(CustomException.class, () -> buyTogetherService.createNewBuyTogetherPost(user, buyTogetherDto));
    }

    @Test
    @DisplayName("아껴쓰기 - 같이사요 | 게시물 실패 테스트 | 구매 예정일이 과거인 경우")
    void createNewBuyTogetherPost_Failure3() {
        // Given
        buyTogetherDto.setBuyDate(LocalDate.now().minusDays(1));

        // When & Then
        assertThrows(CustomException.class, () -> buyTogetherService.createNewBuyTogetherPost(user, buyTogetherDto));
    }
}
