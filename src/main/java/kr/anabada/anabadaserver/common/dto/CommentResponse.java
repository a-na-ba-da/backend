package kr.anabada.anabadaserver.common.dto;

import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentResponse {
    private CommentItem parentComment;
    private List<CommentItem> childComments = new ArrayList<>();

    public CommentResponse(CommentItem parentComment, List<CommentItem> childComments) {
        this.parentComment = parentComment;
        this.childComments = childComments;
    }

    @Getter
    @SuperBuilder
    public static class CommentItem extends LocalDateResponse {
        private final Long id;
        private final UserDto writer;
        private final String content;

        public CommentItem(LocalDateResponseBuilder<?, ?> b, Long id, UserDto writer, String content) {
            super(b);
            this.id = id;
            this.writer = writer;
            this.content = content;
        }
    }
}
