package kr.anabada.anabadaserver.domain.save.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.anabada.anabadaserver.domain.save.entity.BuyTogether;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "같이 사요 생성 (택배 전달) request")
public class BuyTogetherParcelRequest extends BuyTogetherRequest {

    @Builder
    public BuyTogetherParcelRequest(String title, String content, String productUrl, LocalDate buyDate, Integer pay, List<String> images, String buyPlaceDetail) {
        super(title, content, productUrl, buyDate, pay, images, true, buyPlaceDetail);
    }

    @Override
    public Save toEntity() {
        return BuyTogether.builder()
                .title(super.getTitle())
                .content(super.getContent())
                .productUrl(super.getProductUrl())
                .buyPlaceDetail(super.getBuyPlaceDetail())
                .isParcelDelivery(true)
                .buyDate(super.getBuyDate())
                .pay(super.getPay())
                .build();
    }
}
