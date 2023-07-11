package kr.anabada.anabadaserver.domain.save.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "아껴쓰기 검색 요청")
public class SaveSearchRequestDto {
    @Schema(description = "온라인 구매만 검색 여부", example = "true")
    private Boolean onlyOnlineBought;
    @Schema(description = "나의 위도", example = "37.123456")
    private Double lat;
    @Schema(description = "나의 경도", example = "127.123456")
    private Double lng;
    @Schema(description = "검색 반경 (meter)", example = "1000")
    private Double distance;

    public SaveSearchRequestDto(boolean onlyOnlineBought, Double lat, Double lng, Double distance) {
        this.onlyOnlineBought = onlyOnlineBought;
        this.lat = lat;
        this.lng = lng;
        this.distance = distance;
    }

    public boolean fullySetLocationInfo() {
        return lat != null && lng != null && distance != null;
    }

    public Boolean onlyOnlineBought() {
        if (onlyOnlineBought == null)
            return false;
        return onlyOnlineBought;
    }
}
