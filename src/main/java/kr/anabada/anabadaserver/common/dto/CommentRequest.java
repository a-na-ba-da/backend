package kr.anabada.anabadaserver.common.dto;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@Schema(description = "댓글 작성 DTO")
public class CommentRequest {

    @NotNull(message = "댓글 내용을 입력해주세요.")
    @Length(max = 500, message = "댓글 내용은 500자 이내로 입력해주세요.")
    @Parameter(description = "댓글 내용")
    private String content;

    @Nullable
    @Parameter(description = "대댓글이라면, 모체 댓글 id을 넣어주세요", required = false)
    private Long parentCommentId;

    @Builder
    public CommentRequest(String content, Long parentCommentId) {
        this.content = content;
        this.parentCommentId = parentCommentId;
    }

    @Hidden
    public boolean isReplyComment() {
        return (parentCommentId != null);
    }
}
