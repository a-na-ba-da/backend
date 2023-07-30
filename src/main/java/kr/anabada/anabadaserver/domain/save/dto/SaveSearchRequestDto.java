package kr.anabada.anabadaserver.domain.save.dto;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class SaveSearchRequestDto {
    private Boolean onlyOnlineBought;
    private Double lat;
    private Double lng;
    private Double distance;
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
        return lat != null && lng != null && distance != null;
    }

    public Boolean onlyOnlineBought() {
        if (onlyOnlineBought == null)
            return false;
        return onlyOnlineBought;
    }
}
