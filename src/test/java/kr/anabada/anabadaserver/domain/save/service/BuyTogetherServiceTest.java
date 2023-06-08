package kr.anabada.anabadaserver.domain.save.service;

import kr.anabada.anabadaserver.domain.save.dto.BuyTogetherDto;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import kr.anabada.anabadaserver.domain.save.repository.SaveRepository;
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

@ExtendWith(MockitoExtension.class)
class BuyTogetherServiceTest {
    @Mock
    private SaveRepository saveRepository;

    @InjectMocks
    private BuyTogetherService buyTogetherService;

    @Test
    @Transactional
    @DisplayName("아껴쓰기 - 같이사요 | 게시물 등록 성공 테스트")
    void testCreateNewBuyTogetherPostSuccess() {
        // Given
        long fakePostId = 1L;
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
        // Mocking
        given(saveRepository.save(any(Save.class)))
                .willReturn(buyTogether);
        given(saveRepository.findById(fakePostId))
                .willReturn(Optional.of(buyTogether));
        // When
        Save result = buyTogetherService.createNewBuyTogetherPost(fakePostId, buyTogetherDto);
        // Then
        Save findPost = saveRepository.findById(result.getId()).get();
        assertEquals(buyTogetherDto.getTitle(), findPost.getTitle());
    }


}
