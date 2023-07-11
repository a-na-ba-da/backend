package kr.anabada.anabadaserver.domain.save.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.anabada.anabadaserver.common.dto.LocalDateResponse;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@Schema(description = "같이알아요 응답 값")
public class KnowTogetherResponse extends LocalDateResponse {

    @Schema(description = "index")
    private Long id;
    @Schema(description = "작성자")
    private UserDto writer;
    @Schema(description = "제목")
    private String title;
    @Schema(description = "내용")
    private String content;
    @Schema(description = "이미지 리스트")
    private List<String> images;

    // 온/오프라인 구매 관련 필드
    @Schema(description = "온라인 구매 여부")
    private Boolean isOnline;
    @Schema(description = "온라인 구매라면, 상품 주소")
    private String productUrl;
    @Schema(description = "오프라인 구매라면, 구매 장소의 위도")
    private Double buyPlaceLat;
    @Schema(description = "오프라인 구매라면, 구매 장소의 경도")
    private Double buyPlaceLng;
    @Schema(description = "오프라인 구매라면, 구매 장소의 상호 혹은 주소 문자열")
    private String buyPlaceDetail;
}