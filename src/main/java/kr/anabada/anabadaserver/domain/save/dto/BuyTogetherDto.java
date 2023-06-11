package kr.anabada.anabadaserver.domain.save.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import kr.anabada.anabadaserver.domain.save.entity.BuyTogether;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import kr.anabada.anabadaserver.global.exception.CustomException;
import kr.anabada.anabadaserver.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BuyTogetherDto {
    private Long id;
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    @Pattern(regexp = "^(http|https)://.*", message = "URL 형식이 올바르지 않습니다.")
    private String productUrl;
    private Double buyPlaceLat;
    private Double buyPlaceLng;
    private boolean isOnlineDelivery;
    @NotNull(message = "구매일을 입력해주세요.")
    private LocalDate buyDate;
    @NotNull(message = "상대가 내야될 돈을 입력해주세요.")
    private Integer pay;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @JsonProperty("writer")
    private UserDto userDto;
    private List<String> images;

    public Save toEntity() {
        return BuyTogether.builder()
                .id(id)
                .title(title)
                .content(content)
                .productUrl(productUrl)
                .buyPlaceLat(buyPlaceLat)
                .buyPlaceLng(buyPlaceLng)
                .buyDate(buyDate)
                .pay(pay)
                .isOnlineDelivery(isOnlineDelivery)
                .build();
    }

    public void validate() {
        if (!isOnlineDelivery() && placeIsNull()) {
            // 대면 전달인데, 장소 정보가 없는 경우
            throw new CustomException(ErrorCode.NOT_EXIST_BUY_PLACE);
        }

        if (images.isEmpty()) {
            // 이미지를 업로드 하지 않았을때
            throw new CustomException(ErrorCode.NOT_EXIST_IMAGE);
        }

        if (buyDate.isBefore(LocalDate.now())) {
            // 구매일이 현재보다 이전일때
            throw new CustomException(ErrorCode.INVALID_BUY_DATE);
        }
    }

    private boolean placeIsNull() {
        return buyPlaceLat == null || buyPlaceLng == null;
    }
}
