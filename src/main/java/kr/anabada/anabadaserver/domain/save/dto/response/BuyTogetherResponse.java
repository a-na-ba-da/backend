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

    private Long id;
    private String title;
    private String content;
    private String productUrl;
    private Double buyPlaceLat;
    private Double buyPlaceLng;
    private boolean isParcelDelivery;
    private LocalDate buyDate;
    private Integer pay;
    private UserDto writer;
    private List<String> images;
}