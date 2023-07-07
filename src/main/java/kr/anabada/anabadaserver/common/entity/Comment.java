package kr.anabada.anabadaserver.common.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "post_type", length = 15)
    private String postType;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @OneToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @Column(name = "is_removed", nullable = false)
    private boolean isRemoved = false;

    @Builder
    public Comment(Long id, String postType, Long postId, User writer, String content, Long parentCommentId, boolean isRemoved) {
        this.id = id;
        this.postType = postType;
        this.postId = postId;
        this.writer = writer;
        this.content = content;
        this.parentCommentId = parentCommentId;
        this.isRemoved = isRemoved;
    }
}