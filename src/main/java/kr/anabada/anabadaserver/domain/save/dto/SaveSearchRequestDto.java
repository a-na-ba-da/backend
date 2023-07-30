package kr.anabada.anabadaserver.domain.save.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "검색 요청. 위치 기반 검색을 활성화 하려면, lat, lng, distance 필드를 모두 입력해주세요.")
public class SaveSearchRequestDto {
    @Schema(description = "온라인 구매만 검색하려면, true 로 설정해주세요.")
    private Boolean onlyOnlineBought;
    @Schema(description = "위도")
    private Double lat;
    @Schema(description = "경도")
    private Double lng;
    @Schema(description = "검색 거리 (단위: m)")
    private Double distance;
    @Schema(description = "검색 기능을 활성화 하려면, 해당 필드에 2자 이상의 검색어를 입력해주세요.")
    private String keyword;

    public SaveSearchRequestDto(boolean onlyOnlineBought, Double lat, Double lng, Double distance, String keyword) {
        this.onlyOnlineBought = onlyOnlineBought;
        this.lat = lat;
        this.lng = lng;
        this.distance = distance;
        this.keyword = keyword;
    }

    @Hidden
    public boolean isEnableKeywordSearch() {
        if (this.keyword == null)
            return false;

        String trimmedKeyword = this.keyword.trim();
        if (StringUtils.hasText(trimmedKeyword)) {
            if (trimmedKeyword.length() < 2)
                throw new IllegalArgumentException("검색어는 공백 제외 2자 이상 입력해주세요.");
            return true;
        }

        return false;
    }

    public boolean fullySetLocationInfo() {
        if (distance != null) {
            if (distance < 0)
                throw new IllegalArgumentException("거리는 0 이상의 값을 입력해주세요.");

            if (distance > 50000)
                throw new IllegalArgumentException("거리는 50000 이하의 값을 입력해주세요.");
        }
        return lat != null && lng != null && distance != null;
    }

    public Boolean onlyOnlineBought() {
        if (onlyOnlineBought == null)
            return false;
        return onlyOnlineBought;
    }
}
