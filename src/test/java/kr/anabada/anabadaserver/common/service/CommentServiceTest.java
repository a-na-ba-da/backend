package kr.anabada.anabadaserver.common.service;

import jakarta.persistence.EntityManager;
import kr.anabada.anabadaserver.common.dto.CommentRequest;
import kr.anabada.anabadaserver.common.dto.CommentResponse;
import kr.anabada.anabadaserver.domain.ServiceTestWithoutImageUpload;
import kr.anabada.anabadaserver.domain.save.dto.request.BuyTogetherRequest;
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

import java.util.List;

import static kr.anabada.anabadaserver.fixture.dto.BuyTogetherFixture.createBuyTogetherParcel;
import static kr.anabada.anabadaserver.fixture.entity.UserFixture.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@Transactional
@DisplayName("댓글 서비스에서 - CommentService")
class CommentServiceTest extends ServiceTestWithoutImageUpload {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BuyTogetherService buyTogetherService;

    @Autowired
    private EntityManager em;

    @Nested
    @DisplayName("댓글을 조회할 때 - getPostComments()")
    class readComment {

        @Test
        @DisplayName("대댓글도 함께 조회된다.")
        void read_comment_success() {
            // given
            User user = createUser("user@naver.com", "댓글작성자닉네임");
            em.persist(user);

            BuyTogetherRequest post = createBuyTogetherParcel();
            long postId = buyTogetherService.createNewBuyTogetherPost(user, post).getId();

            CommentRequest parentCommentRequest = CommentRequest.builder()
                    .content("부모 댓글 내용")
                    .parentCommentId(null)
                    .build();
            long parentCommentId = commentService.writeNewComment(user, "buy-together", postId, parentCommentRequest);

            CommentRequest childCommentRequest = CommentRequest.builder()
                    .content("대댓글 내용")
                    .parentCommentId(parentCommentId)
                    .build();

            commentService.writeNewComment(user, "buy-together", postId, childCommentRequest);
            //todo : em.clear()없이 대댓글이 정상적으로 조회되도록 수정 - 성훈
            em.clear();

            // when
            List<CommentResponse> result = commentService.getPostComments("buy-together", postId);

            // then
            assertThat(result.get(0).getParentComment())
                    .extracting("id", "content")
                    .containsExactly(parentCommentId, "부모 댓글 내용");

            assertThat(result.get(0).getChildComments())
                    .extracting("writer.nickname", "content")
                    .containsExactly(tuple("댓글작성자닉네임", "대댓글 내용"));

        }
    }

    @Nested
    @DisplayName("댓글을 작성할 때 - writeNewComment()")
    class writeComment {

        @Test
        @DisplayName("모든 인자값이 정상이라면 부모 댓글이 작성된다.")
        void success_parent_comment() {
            // given
            User user = createUser("user@naver.com", "1234");
            em.persist(user);

            BuyTogetherRequest post = createBuyTogetherParcel();
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
            User user = createUser("user@naver.com", "1234");
            em.persist(user);

            BuyTogetherRequest post = createBuyTogetherParcel();
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
            User user = createUser("user@naver.com", "1234");
            em.persist(user);

            BuyTogetherRequest post = createBuyTogetherParcel();
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
        @DisplayName("작성자가 없으면 DataIntegrityViolationException 예외가 발생한다.")
        void comment_writer_null_error() {
            // given
            User user = createUser("user@naver.com", "1234");
            em.persist(user);

            BuyTogetherRequest post = createBuyTogetherParcel();
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
            User user = createUser("user@naver.com", "1234");
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
            User user = createUser("user@naver.com", "1234");
            em.persist(user);

            BuyTogetherRequest post = createBuyTogetherParcel();
            long postId = buyTogetherService.createNewBuyTogetherPost(user, post).getId();

            CommentRequest commentRequest = CommentRequest.builder()
                    .content("content")
                    .parentCommentId(null)
                    .build();

            String wrongPostType = "wrongPostType";

            // when & then
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                commentService.writeNewComment(user, wrongPostType, postId, commentRequest);
            }, "postType 인자값이 잘못되었습니다.");
        }

        @Test
        @DisplayName("댓글 내용이 길면(500자 초과) 작성할 수 없다.")
        void cant_comment_content_too_long() {
            // given
            User user = createUser("user@naver.com", "1234");
            em.persist(user);

            BuyTogetherRequest post = createBuyTogetherParcel();
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

        @Test
        @DisplayName("대댓글에 대댓글을 작성할 수 없다 = 댓글의 최대 depth는 2단계이다.")
        void cant_comment_sub_comment() {
            // given
            User user = createUser("james@naver.com", "1234");
            em.persist(user);

            BuyTogetherRequest post = createBuyTogetherParcel();
            long postId = buyTogetherService.createNewBuyTogetherPost(user, post).getId();

            CommentRequest commentRequest = CommentRequest.builder()
                    .content("첫번째 댓글")
                    .parentCommentId(null)
                    .build();
            Long parentCommentId = commentService.writeNewComment(user, "buy-together", postId, commentRequest);

            CommentRequest subCommentRequest = CommentRequest.builder()
                    .content("대댓글")
                    .parentCommentId(parentCommentId)
                    .build();
            Long subCommentId = commentService.writeNewComment(user, "buy-together", postId, subCommentRequest);
            ReflectionTestUtils.setField(subCommentRequest, "parentCommentId", subCommentId);

            // when & then
            Assertions.assertThrows(IllegalArgumentException.class, () -> {
                commentService.writeNewComment(user, "buy-together", postId, subCommentRequest);
            }, "댓글의 depth는 2단계까지만 가능합니다.");
        }
    }
}