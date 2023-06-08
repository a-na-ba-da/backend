package kr.anabada.anabadaserver.domain.save.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.anabada.anabadaserver.domain.save.entity.BuyTogether;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class BuyTogetherDto {
    private Long id;
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    private String content;
    @NotBlank(message = "상품 URL을 입력해주세요.")
    private String productUrl;
    private Double buyPlaceLat;
    private Double buyPlaceLng;
    @NotNull(message = "true, false 값을 포함해야합니다.")
    private Boolean isOnlineDelivery;
    @NotNull(message = "구매일을 입력해주세요.")
    private LocalDate buyDate;
    @NotNull(message = "상대가 내야될 돈을 입력해주세요.")
    private Integer pay;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @JsonProperty("writer")
    private UserDto userDto;

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
}
