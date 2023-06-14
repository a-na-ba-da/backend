package kr.anabada.anabadaserver.domain.save.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import kr.anabada.anabadaserver.domain.save.entity.BuyTogether;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import kr.anabada.anabadaserver.global.exception.CustomException;
import kr.anabada.anabadaserver.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class BuyTogetherDto {
    private Long id;
    @NotNull(message = "제목을 입력해주세요.")
    @Length(min = 1, max = 50, message = "제목은 1자 이상 50자 이하로 작성해주세요.")
    private String title;
    @NotNull(message = "내용을 입력해주세요.")
    @Length(min = 1, max = 300, message = "내용은 1자 이상 50자 이하로 작성해주세요.")
    private String content;
    @Pattern(regexp = "^(http|https)://.*", message = "URL은 http:// 또는 https:// 로 시작해야합니다.")
    @Length(max = 500, message = "상품 URL은 500자 이하로 작성해주세요.")
    private String productUrl;
    @Range(min = -90, max = 90, message = "위도의 범위는 -90 ~ 90 입니다.")
    private Double buyPlaceLat;
    @Range(min = -180, max = 180, message = "경도의 범위는 -180 ~ 180 입니다.")
    private Double buyPlaceLng;
    private boolean isOnlineDelivery;
    @NotNull(message = "구매일을 입력해주세요.")
    private LocalDate buyDate;
    @NotNull(message = "상대가 지불해야 할 돈을 입력해주세요.")
    @Positive(message = "상대가 지불해야 할 돈은 0 보다 커야합니다.")
    private Integer pay;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @JsonProperty("writer")
    private UserDto userDto;
    @NotNull(message = "이미지를 업로드 해주세요.")
    @Size(min = 1, max = 5, message = "이미지는 1개 이상 5개 이하로 업로드 해주세요.")
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

        if (images == null || images.isEmpty()) {
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
