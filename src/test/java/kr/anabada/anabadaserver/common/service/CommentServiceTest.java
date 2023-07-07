package kr.anabada.anabadaserver.common.service;

import jakarta.persistence.EntityManager;
import kr.anabada.anabadaserver.common.dto.CommentRequest;
import kr.anabada.anabadaserver.domain.ServiceTestWithoutImageUpload;
import kr.anabada.anabadaserver.domain.save.dto.BuyTogetherDto;
import kr.anabada.anabadaserver.domain.save.service.BuyTogetherService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional
@DisplayName("댓글 서비스에서 - CommentService")
class CommentServiceTest extends ServiceTestWithoutImageUpload {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BuyTogetherService buyTogetherService;

    @Autowired
    private EntityManager em;

    private User createUserA() {
        return User.builder()
                .nickname("userA")
                .email("userA@test.com")
                .activated(true)
                .role("ROLE_USER")
                .build();
    }

    @Nested
    @DisplayName("댓글을 작성할 때 - writeNewComment()")
    class writeComment {

        @Test
        @DisplayName("모든 인자값이 정상이라면 부모 댓글이 작성된다.")
        void success_parent_comment() {
            // given
            User user = createUserA();
            em.persist(user);

            BuyTogetherDto post = BuyTogetherDto.builder()
                    .title("title")
                    .content("content")
                    .isOnlineDelivery(true)
                    .productUrl("https://naver.com")
                    .buyDate(LocalDate.from(LocalDateTime.now().plusDays(2)))
                    .images(List.of("image"))
                    .build();
            long postId = buyTogetherService.createNewBuyTogetherPost(user, post).getId();

            CommentRequest commentRequest = CommentRequest.builder()
                    .content("댓글 내용")
                    .parentCommentId(null)
                    .build();

            // when & then
            Assertions.assertDoesNotThrow(() -> {
                commentService.writeNewComment(user, "buy-together", postId, commentRequest);
            });
        }

        @Test
        @DisplayName("모든 인자값이 정상이라면 대댓글이 작성된다.")
        void success_sub_comment() {
            // given
            User user = createUserA();
            em.persist(user);

            BuyTogetherDto post = BuyTogetherDto.builder()
                    .title("title")
                    .content("content")
                    .isOnlineDelivery(true)
                    .productUrl("https://naver.com")
                    .buyDate(LocalDate.from(LocalDateTime.now().plusDays(2)))
                    .images(List.of("image"))
                    .build();
            long postId = buyTogetherService.createNewBuyTogetherPost(user, post).getId();

            CommentRequest commentRequest = CommentRequest.builder()
                    .content("댓글 내용")
                    .parentCommentId(null)
                    .build();
            Long parentCommentId = commentService.writeNewComment(user, "buy-together", postId, commentRequest);
            ReflectionTestUtils.setField(commentRequest, "parentCommentId", parentCommentId);

            // when & then
            Assertions.assertDoesNotThrow(() -> {
                commentService.writeNewComment(user, "buy-together", postId, commentRequest);
            });
        }


        @Test
        @DisplayName("댓글 내용이 없으면 IAE 예외가 발생한다.")
        void comment_null_error() {
            // given
            User user = createUserA();
            em.persist(user);

            BuyTogetherDto post = BuyTogetherDto.builder()
                    .title("title")
                    .content("content")
                    .isOnlineDelivery(true)
                    .productUrl("https://naver.com")
                    .buyDate(LocalDate.from(LocalDateTime.now().plusDays(2)))
                    .images(List.of("image"))
                    .build();
            long postId = buyTogetherService.createNewBuyTogetherPost(user, post).getId();

            CommentRequest commentRequest = CommentRequest.builder()
                    .content(null)
                    .parentCommentId(null)
                    .build();

            // when & then
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                commentService.writeNewComment(user, "buy-together", postId, commentRequest);
            }, "댓글 내용을 입력해주세요.");
        }

        @Test
        @DisplayName("작성자가 없으면 IAE 예외가 발생한다.")
        void comment_writer_null_error() {
            // given
            User user = createUserA();
            em.persist(user);

            BuyTogetherDto post = BuyTogetherDto.builder()
                    .title("title")
                    .content("content")
                    .isOnlineDelivery(true)
                    .productUrl("https://naver.com")
                    .buyDate(LocalDate.from(LocalDateTime.now().plusDays(2)))
                    .images(List.of("image"))
                    .build();
            long postId = buyTogetherService.createNewBuyTogetherPost(user, post).getId();

            CommentRequest commentRequest = CommentRequest.builder()
                    .content("content")
                    .parentCommentId(null)
                    .build();

            // when & then
            Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
                commentService.writeNewComment(null, "buy-together", postId, commentRequest);
            });
        }

        @Test
        @DisplayName("존재하지 않는 게시물 id(postId)에 댓글을 작성하면 IAE 예외가 발생한다.")
        void cant_comment_notExist_postId() {
            // given
            User user = createUserA();
            em.persist(user);

            CommentRequest commentRequest = CommentRequest.builder()
                    .content("content")
                    .parentCommentId(null)
                    .build();

            Long postId = 999L;
            // when & then
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                commentService.writeNewComment(user, "buy-together", postId, commentRequest);
            }, "해당 게시물이 존재하지 않습니다.");
        }

        @Test
        @DisplayName("존재하지 않는 게시판(post_type)에 댓글을 작성하면 IAE 예외가 발생한다.")
        void cant_comment_notExist_postType() {
            // given
            User user = createUserA();
            em.persist(user);

            BuyTogetherDto post = BuyTogetherDto.builder()
                    .title("title")
                    .content("content")
                    .isOnlineDelivery(true)
                    .productUrl("https://naver.com")
                    .buyDate(LocalDate.from(LocalDateTime.now().plusDays(2)))
                    .images(List.of("image"))
                    .build();
            long postId = buyTogetherService.createNewBuyTogetherPost(user, post).getId();

            CommentRequest commentRequest = CommentRequest.builder()
                    .content("content")
                    .parentCommentId(null)
                    .build();

            String wrongPostType = "wrongPostType";

            // when & then
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                commentService.writeNewComment(user, "wrongPostType", postId, commentRequest);
            }, "postType 인자값이 잘못되었습니다.");
        }

        @Test
        @DisplayName("댓글 내용이 길면(500자 초과) 작성할 수 없다.")
        void cant_comment_content_too_long() {
            // given
            User user = createUserA();
            em.persist(user);

            BuyTogetherDto post = BuyTogetherDto.builder()
                    .title("title")
                    .content("content")
                    .isOnlineDelivery(true)
                    .productUrl("https://naver.com")
                    .buyDate(LocalDate.from(LocalDateTime.now().plusDays(2)))
                    .images(List.of("image"))
                    .build();
            long postId = buyTogetherService.createNewBuyTogetherPost(user, post).getId();

            CommentRequest commentRequest = CommentRequest.builder()
                    .content("1".repeat(501))
                    .parentCommentId(null)
                    .build();

            // when & then
            Assertions.assertThrowsExactly(InvalidDataAccessResourceUsageException.class, () -> {
                commentService.writeNewComment(user, "buy-together", postId, commentRequest);
            });
        }
    }
}