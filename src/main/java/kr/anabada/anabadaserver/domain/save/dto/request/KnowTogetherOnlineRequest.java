package kr.anabada.anabadaserver.domain.save.dto.request;

import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import kr.anabada.anabadaserver.domain.save.entity.KnowTogether;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@Schema(description = "온라인 구매 꿀팁 request")
public class KnowTogetherOnlineRequest extends KnowTogetherRequest {

    @Schema(description = "상품 URL")
    @NotEmpty(message = "상품 URL을 입력해주세요.")
    @Pattern(regexp = "^(http|https)://.*", message = "URL은 http:// 또는 https:// 로 시작해야합니다.")
    @Length(max = 500, message = "상품 URL은 500자 이하로 작성해주세요.")
    private String productUrl;

    public KnowTogetherOnlineRequest(String title, String content, List<String> images, String productUrl) {
        super(title, content, true, images);
        this.productUrl = productUrl;
    }

    @Override
    public void checkValidation() {
        if (StringUtils.isEmpty(productUrl)) {
            throw new IllegalArgumentException("온라인 구매인 경우, 상품 주소를 입력해주세요.");
        }
    }

    @Override
    public Save toEntity() {
        return KnowTogether.builder()
                .title(super.getTitle())
                .content(super.getContent())
                .productUrl(productUrl)
                .isOnline(true)
                .build();
    }

    @Valid
    @Override
    public String getTitle() {
        return super.getContent();
    }
}