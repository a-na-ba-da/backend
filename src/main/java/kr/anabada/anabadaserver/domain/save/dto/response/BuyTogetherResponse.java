package kr.anabada.anabadaserver.domain.save.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.anabada.anabadaserver.common.dto.LocalDateResponse;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@SuperBuilder
@Schema(description = "같이사요 응답 값")
public class BuyTogetherResponse extends LocalDateResponse {

    @Schema(description = "index")
    private Long id;
    @Schema(description = "제목")
    private String title;
    @Schema(description = "내용")
    private String content;
    @Schema(description = "구매 예정일")
    private LocalDate buyDate;
    @Schema(description = "너가 내야 할 금액")
    private Integer pay;
    @Schema(description = "작성자")
    private UserDto writer;
    @Schema(description = "이미지들")
    private List<String> images;

    // 온라인 구매
    @Schema(description = "온라인 구매 여부")
    private boolean isOnlineDeal;
    @Schema(description = "온라인 구매가 아니라면, 구매 장소의 상호 혹은 주소 문자열")
    private String buyPlaceDetail;
    @Schema(description = "온라인 구매 라면, 구매할 상품의 URL")
    private String productUrl;

    // 택배 전달
    @Schema(description = "택배 전달 여부")
    private boolean isParcelDelivery;
    @Schema(description = "택배 전달이 아닌경우 (비대면), 물건을 전달할 인근 위도")
    private Double deliveryPlaceLat;
    @Schema(description = "택배 전달이 아닌경우 (비대면), 물건을 전달할 인근 경도")
    private Double deliveryPlaceLng;
}