package kr.anabada.anabadaserver.domain.save.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kr.anabada.anabadaserver.domain.save.entity.KnowTogether;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class KnowTogetherDto {
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
    @NotNull(message = "온라인 구매 여부를 선택해주세요.")
    private Boolean isOnlineBought;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    @JsonProperty("writer")
    private UserDto userDto;
    @NotNull(message = "이미지를 업로드 해주세요.")
    @Size(min = 1, max = 5, message = "이미지는 1개 이상 5개 이하로 업로드 해주세요.")
    private List<String> images;

    public Save toEntity() {
        if (Boolean.TRUE.equals(isOnlineBought))
            return KnowTogether.builder()
                    .id(id)
                    .title(title)
                    .content(content)
                    .productUrl(productUrl)
                    .isOnline(isOnlineBought)
                    .build();
        else
            return KnowTogether.builder()
                    .id(id)
                    .title(title)
                    .content(content)
                    .buyPlaceLat(buyPlaceLat)
                    .buyPlaceLng(buyPlaceLng)
                    .isOnline(isOnlineBought)
                    .build();
    }

    public void validate() {
        // 온라인 구매인데, 상품 URL이 없는 경우
        if (Boolean.TRUE.equals(isOnlineBought) && StringUtils.isEmpty(productUrl)) {
            throw new IllegalArgumentException("온라인 구매인 경우, 상품 URL을 입력해주세요.");
        }

        if (Boolean.FALSE.equals(isOnlineBought) && (buyPlaceLat == null || buyPlaceLng == null)) {
            throw new IllegalArgumentException("오프라인 구매인 경우, 구매 장소를 지도에서 선택해주세요.");
        }
    }


}