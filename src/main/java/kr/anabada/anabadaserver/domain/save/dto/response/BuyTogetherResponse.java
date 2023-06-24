package kr.anabada.anabadaserver.domain.save.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "같이사요 응답 값")
public class BuyTogetherResponse {

    private Long id;
    private String title;
    private String content;
    private String productUrl;
    private Double buyPlaceLat;
    private Double buyPlaceLng;
    private boolean isParcelDelivery;
    private LocalDate buyDate;
    private Integer pay;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private UserDto writer;
    private List<String> images;

    @Builder
    public BuyTogetherResponse(Long id, String title, String content, String productUrl, Double buyPlaceLat, Double buyPlaceLng, boolean isParcelDelivery, LocalDate buyDate, Integer pay, LocalDateTime createdAt, LocalDateTime modifiedAt, UserDto writer, List<String> images) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.productUrl = productUrl;
        this.buyPlaceLat = buyPlaceLat;
        this.buyPlaceLng = buyPlaceLng;
        this.isParcelDelivery = isParcelDelivery;
        this.buyDate = buyDate;
        this.pay = pay;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.writer = writer;
        this.images = images;
    }
}