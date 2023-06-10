package kr.anabada.anabadaserver.domain.save.service;

import kr.anabada.anabadaserver.common.service.ImageService;
import kr.anabada.anabadaserver.domain.save.dto.BuyTogetherDto;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import kr.anabada.anabadaserver.domain.save.repository.SaveRepository;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    @Transactional
    @DisplayName("아껴쓰기 - 같이사요 | 게시물 등록 성공 테스트")
    void 게시물_등록_성공() {
        // Given
        long fakePostId = 1L;
        User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .role("ROLE_USER")
                .activated(true)
                .nickname("testtest")
                .build();
        BuyTogetherDto buyTogetherDto = BuyTogetherDto.builder()
                .title("title")
                .content("content")
                .pay(10000)
                .isOnlineDelivery(true)
                .productUrl("https://www.naver.com")
                .buyDate(LocalDate.now())
                .createdAt(LocalDateTime.now())
                .build();
        Save buyTogether = buyTogetherDto.toEntity();
        ReflectionTestUtils.setField(buyTogether, "id", fakePostId);
        // When - Mocking
        given(saveRepository.save(any(Save.class)))
                .willReturn(buyTogether);
        given(saveRepository.findById(fakePostId))
                .willReturn(Optional.of(buyTogether));
        doNothing().when(imageService).attach(any(Long.class), any(), any(Long.class));
        // When
        Save result = buyTogetherService.createNewBuyTogetherPost(user, buyTogetherDto);
        // Then
        Save findPost = saveRepository.findById(result.getId()).get();
        assertEquals(buyTogetherDto.getTitle(), findPost.getTitle());
    }

}
