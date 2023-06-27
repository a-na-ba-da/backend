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

    private Long id;
    private UserDto writer;
    private String title;
    private String content;
    private String productUrl;
    private Double deliveryPlaceLat;
    private Double deliveryPlaceLng;
    private Boolean isOnline;
    private String buyPlaceDetail;
    private List<String> images;
}