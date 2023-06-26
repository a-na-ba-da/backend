package kr.anabada.anabadaserver.domain.save.dto.request;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@Schema(description = "같이알아요 요청")
public abstract class KnowTogetherRequest {

    @Schema(description = "제목")
    @NotEmpty(message = "제목을 입력해주세요.")
    @Length(min = 2, max = 30, message = "제목은 2자 이상 30자 이하로 작성해주세요.")
    private String title;

    @Schema(description = "내용")
    @NotEmpty(message = "내용을 입력해주세요.")
    @Length(min = 5, max = 700, message = "내용은 5자 이상 700자 이하로 작성해주세요.")
    private String content;

    @Hidden
    private boolean onlineBought;

    @Schema(description = "이미지")
    @NotEmpty(message = "이미지를 업로드 해주세요.")
    @Size(min = 1, max = 5, message = "이미지는 1개 이상 5개 이하로 업로드 해주세요.")
    private List<String> images;

    protected KnowTogetherRequest(String title, String content, boolean isOnlineBought, List<String> images) {
        this.title = title;
        this.content = content;
        this.onlineBought = isOnlineBought;
        this.images = images;
    }

    public void checkValidation() {
        // check field validation
        if (!StringUtils.hasText(title) && (title.length() <= 2 || title.length() >= 30)) {
            throw new IllegalArgumentException("제목은 2자 이상 30자 이하로 작성해주세요.");
        }

        if (!StringUtils.hasText(title) && (title.length() <= 5 || title.length() >= 30)) {
            throw new IllegalArgumentException("제목은 2자 이상 30자 이하로 작성해주세요.");
        }

    }

    public Save toEntity() {
        throw new IllegalStateException("데이터를 변환할 수 없습니다.");
    }
}
