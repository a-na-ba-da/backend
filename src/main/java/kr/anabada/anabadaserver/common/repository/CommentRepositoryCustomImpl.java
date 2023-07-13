package kr.anabada.anabadaserver.common.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.anabada.anabadaserver.common.dto.CommentResponse;
import kr.anabada.anabadaserver.common.entity.Comment;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static kr.anabada.anabadaserver.common.entity.QComment.comment;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CommentResponse> getCommentsByPost(String postType, Long postId) {
        List<Comment> result = queryFactory.selectFrom(comment)
                .leftJoin(comment.childComments).fetchJoin()
                .leftJoin(comment.writer).fetchJoin()
                .where(checkPost(postType, postId)
                        .and(isParentComment()))
                .fetch();

        return commentToResponse(result);
    }

    private BooleanExpression isParentComment() {
        return comment.parentComment.isNull();
    }

    private BooleanExpression checkPost(String postType, Long postId) {
        return comment.postType.eq(postType)
                .and(comment.postId.eq(postId));
    }

    private List<CommentResponse> commentToResponse(List<Comment> result) {
        List<CommentResponse> response = new ArrayList<>();
        result.forEach(tuple -> {
            CommentResponse.CommentItem parent = CommentResponse.CommentItem.builder()
                    .id(tuple.getId())
                    .writer(Objects.requireNonNull(tuple.getWriter()).toDto())
                    .content(tuple.getContent())
                    .createdAt(tuple.getCreatedAt())
                    .modifiedAt(tuple.getModifiedAt())
                    .build();

            List<CommentResponse.CommentItem> child = new ArrayList<>();

            if (tuple.getChildComments() != null) {
                for (Comment c : tuple.getChildComments()) {
                    child.add(CommentResponse.CommentItem.builder()
                            .id(c.getId())
                            .writer(Objects.requireNonNull(c.getWriter()).toDto())
                            .content(c.getContent())
                            .createdAt(c.getCreatedAt())
                            .modifiedAt(c.getModifiedAt())
                            .build());
                }
            }

            response.add(new CommentResponse(parent, child));
        });
        return response;
    }
}
