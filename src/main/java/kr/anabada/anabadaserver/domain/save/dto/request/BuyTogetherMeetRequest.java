package kr.anabada.anabadaserver.domain.save.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.anabada.anabadaserver.domain.save.entity.BuyTogether;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@Schema(description = "같이 사요 생성 (대면 전달) request")
public class BuyTogetherMeetRequest extends BuyTogetherRequest {
    @Schema(description = "물건을 전달할 인근 위도")
    @Range(min = -90, max = 90, message = "위도의 범위는 -90 ~ 90 입니다.")
    private Double deliveryPlaceLat;

    @Schema(description = "물건을 전달할 인근 경도")
    @Range(min = -180, max = 180, message = "경도의 범위는 -180 ~ 180 입니다.")
    private Double deliveryPlaceLng;

    public BuyTogetherMeetRequest(String title, String content, String productUrl, LocalDate buyDate, Integer pay, List<String> images, Double deliveryPlaceLat, Double deliveryPlaceLng) {
        super(title, content, productUrl, buyDate, pay, images, false);
        this.deliveryPlaceLat = deliveryPlaceLat;
        this.deliveryPlaceLng = deliveryPlaceLng;
    }

    @Override
    public Save toEntity() {
        return BuyTogether.builder()
                .title(super.getTitle())
                .content(super.getContent())
                .productUrl(super.getProductUrl())
                .isParcelDelivery(false)
                .placeLat(deliveryPlaceLat)
                .placeLng(deliveryPlaceLng)
                .buyDate(super.getBuyDate())
                .pay(super.getPay())
                .build();
    }

    @Override
    public void checkValidation() {
        super.checkValidation();

        if (placeIsNull())
            throw new IllegalArgumentException("만나서 물건을 전달할 인근 위치를 입력해주세요.");
    }

    private boolean placeIsNull() {
        return deliveryPlaceLat == null || deliveryPlaceLng == null;
    }
}
