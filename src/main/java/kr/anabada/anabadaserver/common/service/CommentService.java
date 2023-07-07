package kr.anabada.anabadaserver.common.service;

import kr.anabada.anabadaserver.common.dto.CommentRequest;
import kr.anabada.anabadaserver.common.entity.Comment;
import kr.anabada.anabadaserver.common.repository.CommentRepository;
import kr.anabada.anabadaserver.domain.save.repository.SaveRepository;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final SaveRepository saveRepository;

    /**
     * 댓글을 작성하는 메서드
     *
     * @param user           댓글 작성자
     * @param postType       게시물 타입
     * @param postId         게시물 id
     * @param commentRequest 댓글 내용등이 담긴 DTO
     */
    @Transactional
    public Long writeNewComment(User user, String postType, Long postId, CommentRequest commentRequest) {
        if (!StringUtils.hasText(postType) || postId == null) {
            throw new IllegalArgumentException("postType과 postId는 null이 될 수 없습니다.");
        }

        if (!StringUtils.hasText(commentRequest.getContent())) {
            throw new IllegalArgumentException("댓글 내용을 입력해주세요.");
        }

        Comment comment = Comment.builder()
                .writer(user)
                .postType(postType)
                .postId(postId)
                .content(commentRequest.getContent())
                .parentCommentId(commentRequest.getParentCommentId())
                .build();

        // check postType && postId exists
        switch (postType) {
            case "buy-together" -> saveRepository.findBuyTogetherById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
            case "know-together" -> saveRepository.findKnowTogetherById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
            default -> throw new IllegalArgumentException("postType 인자값이 잘못되었습니다.");
        }

        if (commentRequest.isReplyComment()) {
            // check parent comment exists
            commentRepository.findById(commentRequest.getParentCommentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않아 대댓글을 달 수 없습니다."));
        }

        return commentRepository.save(comment).getId();
    }
}
