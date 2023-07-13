package kr.anabada.anabadaserver.common.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment")
@Where(clause = "is_removed = false")
@SQLDelete(sql = "UPDATE comment SET is_removed = 1 WHERE id = ?")
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

    @Column(name = "is_removed", nullable = false)
    private boolean isRemoved = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OrderBy("id ASC")
    @BatchSize(size = 100)
    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY)
    private List<Comment> childComments;

    @Builder
    public Comment(Long id, String postType, Long postId, User writer, String content, boolean isRemoved, Comment parentComment, List<Comment> childComments) {
        this.id = id;
        this.postType = postType;
        this.postId = postId;
        this.writer = writer;
        this.content = content;
        this.isRemoved = isRemoved;
        this.parentComment = parentComment;
        this.childComments = childComments;
    }
}