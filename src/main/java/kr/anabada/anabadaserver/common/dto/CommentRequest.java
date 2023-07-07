package kr.anabada.anabadaserver.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor
@Schema(description = "댓글 작성 DTO")
public class CommentRequest {

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    @Length(min = 2, max = 500, message = "댓글 내용은 2자 이상 500자 이하로 입력해주세요.")
    @Schema(description = "댓글 내용", example = "안녕하세요")
    private String content;

    @Schema(description = "대댓글이라면, 모체 댓글 id을 넣어주세요")
    private Long parentCommentId;

    @Builder
    public CommentRequest(String content, Long parentCommentId) {
        this.content = content;
        this.parentCommentId = parentCommentId;
    }

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    public boolean isReplyComment() {
        return (parentCommentId != null);
    }
}
