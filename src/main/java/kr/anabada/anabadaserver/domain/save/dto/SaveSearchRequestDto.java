package kr.anabada.anabadaserver.domain.save.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SaveSearchRequestDto {
    private Boolean onlyOnlineBought;
    private Double lat;
    private Double lng;
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
