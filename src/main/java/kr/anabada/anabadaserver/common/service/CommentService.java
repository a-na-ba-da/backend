package kr.anabada.anabadaserver.common.service;

import kr.anabada.anabadaserver.common.dto.CommentRequest;
import kr.anabada.anabadaserver.common.dto.CommentResponse;
import kr.anabada.anabadaserver.common.entity.Comment;
import kr.anabada.anabadaserver.common.repository.CommentRepository;
import kr.anabada.anabadaserver.domain.save.repository.SaveRepository;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

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
                .parentComment(returnIfChildComment(commentRequest.getParentCommentId()))
                .build();

        // check postType && postId exists
        switch (postType) {
            case "buy-together" -> saveRepository.findBuyTogetherById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."))
                    .increaseCommentCount();
            case "know-together" -> saveRepository.findKnowTogetherById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."))
                    .increaseCommentCount();
            default -> throw new IllegalArgumentException("postType 인자값이 잘못되었습니다.");
        }

        return commentRepository.save(comment).getId();
    }

    private Comment returnIfChildComment(Long parentCommentId) {
        // Check if parentCommentId is null
        if (parentCommentId == null) {
            return null;
        }
        // Find the parent comment using parentCommentId
        Comment parent = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
        // Check if the parent comment has a parent comment (depth is limited to 2 levels)
        if (parent.getParentComment() != null) {
            throw new IllegalArgumentException("댓글의 depth는 2단계까지만 가능합니다.");
        }
        // Return the parent comment
        return parent;
    }


    /**
     * 댓글-대댓글을 조회하는 메서드
     *
     * @param postType 게시판 타입 (buy-together, know-together 등...)
     * @param postId   게시물 id
     * @return 댓글-대댓글 리스트
     */
    public List<CommentResponse> getPostComments(String postType, Long postId) {
        // check postType && postId exists
        switch (postType) {
            case "buy-together" -> saveRepository.findBuyTogetherById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
            case "know-together" -> saveRepository.findKnowTogetherById(postId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 존재하지 않습니다."));
            default -> throw new IllegalArgumentException("postType 인자값이 잘못되었습니다.");
        }

        return commentRepository.getCommentsByPost(postType, postId);
    }
}
