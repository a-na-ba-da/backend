package kr.anabada.anabadaserver.domain.save.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.anabada.anabadaserver.domain.save.entity.KnowTogether;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@Schema(description = "오프라인 구매 꿀팁 request")
public class KnowTogetherOfflineRequest extends KnowTogetherRequest {

    @Schema(description = "구매 장소 위도")
    @Range(min = -90, max = 90, message = "위도의 범위는 -90 ~ 90 입니다.")
    private Double buyPlaceLat;

    @Schema(description = "구매 장소 경도")
    @Range(min = -180, max = 180, message = "경도의 범위는 -180 ~ 180 입니다.")
    private Double buyPlaceLng;

    @Schema(description = "구매 장소에 대한 간단한 설명 (상호명 등등)")
    @Length(max = 20, message = "구매 장소에 대한 상호명은 20자 이하로 작성해주세요.")
    private String buyPlaceDetail;

    public KnowTogetherOfflineRequest(String title, String content, List<String> images, Double buyPlaceLat, Double buyPlaceLng, String buyPlaceDetail) {
        super(title, content, false, images, null);
        this.buyPlaceLat = buyPlaceLat;
        this.buyPlaceLng = buyPlaceLng;
        this.buyPlaceDetail = buyPlaceDetail;
    }

    @Override
    public void checkValidation() {
        if (buyPlaceLat == null || buyPlaceLng == null) {
            throw new IllegalArgumentException("오프라인 구매정보는 구매 장소 위도와 경도를 입력해야 합니다.");
        }
    }

    @Override
    public Save toEntity() {
        return KnowTogether.builder()
                .title(super.getTitle())
                .content(super.getContent())
                .placeLat(buyPlaceLat)
                .placeLng(buyPlaceLng)
                .buyPlaceDetail(buyPlaceDetail)
                .isOnline(false)
                .build();
    }
}