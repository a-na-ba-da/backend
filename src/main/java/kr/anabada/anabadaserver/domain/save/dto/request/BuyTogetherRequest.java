package kr.anabada.anabadaserver.domain.save.dto.request;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "같이사요 요청", subTypes = {BuyTogetherMeetRequest.class, BuyTogetherParcelRequest.class}, discriminatorProperty = "isParcelDelivery")
public abstract class BuyTogetherRequest {

    @Schema(description = "제목")
    @Length(min = 2, max = 30, message = "제목은 2자 이상 30자 이하로 작성해주세요.")
    private String title;

    @Schema(description = "내용")
    @Length(min = 5, max = 700, message = "내용은 5자 이상 700자 이하로 작성해주세요.")
    private String content;

    @Schema(description = "온라인에서 구매 할 상품인 경우, URL")
    @Pattern(regexp = "^(http|https)://.*", message = "URL은 http:// 또는 https:// 로 시작해야합니다.")
    @Length(max = 500, message = "상품 URL은 500자 이하로 작성해주세요.")
    private String productUrl;

    @Schema(description = "오프라인에서 구매 할 상품인 경우, 구매 장소에 대한 간단한 설명 (상호명 등등)")
    @Length(max = 20, message = "구매 장소에 대한 상호명은 20자 이하로 작성해주세요.")
    private String buyPlaceDetail;

    @Schema(description = "구매 예정일")
    @NotNull(message = "구매 예정일을 입력해주세요. (형식: yyyy-MM-dd)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate buyDate;

    @Schema(description = "상대가 지불해야 할 돈 (택배 전달이라면, 배송비 포함)")
    @Positive(message = "상대가 지불해야 할 돈은 0 보다 커야합니다.")
    private Integer pay;

    @Parameter(description = "이미지 파일명 리스트")
    @Size(min = 1, max = 5, message = "이미지는 1개 이상 5개 이하로 업로드 해주세요.")
    private List<String> images;

    @Hidden
    private boolean parcelDelivery;

    protected BuyTogetherRequest(String title, String content, String productUrl, LocalDate buyDate, Integer pay, List<String> images, boolean parcelDelivery, String buyPlaceDetail) {
        this.title = title;
        this.content = content;
        this.productUrl = productUrl;
        this.buyDate = buyDate;
        this.pay = pay;
        this.images = images;
        this.parcelDelivery = parcelDelivery;
        this.buyPlaceDetail = buyPlaceDetail;
    }

    private boolean isOnlineProduct() {
        return StringUtils.hasText(productUrl);
    }

    private boolean isOfflineProduct() {
        return StringUtils.hasText(buyPlaceDetail);
    }

    public void checkValidation() {
        if ((isOnlineProduct() && isOfflineProduct()) ||
                (!isOnlineProduct() && !isOfflineProduct())) {
            throw new IllegalArgumentException("상품 구매처는 온라인 혹은 오프라인 중 하나여야 합니다.");
        }


        if (images == null || images.isEmpty()) {
            // 이미지를 업로드 하지 않았을때
            throw new CustomException(ErrorCode.NOT_EXIST_IMAGE);
        }

        if (buyDate.isBefore(LocalDate.now())) {
            // 구매일이 현재보다 이전일때
            throw new IllegalArgumentException("구매 예정일은 현재보다 이후로 설정해주세요.");
        }

        if (buyDate.isAfter(LocalDate.now().plusMonths(2))) {
            // 구매일이 현재보다 2달 이후일때
            throw new IllegalArgumentException("구매일은 현재로부터 2달 이내로 설정해주세요.");
        }
    }

    public Save toEntity() {
        throw new IllegalStateException("데이터를 변환할 수 없습니다.");
    }
}
