package kr.anabada.anabadaserver.domain.recycle.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.common.entity.BaseTimeEntity;
import kr.anabada.anabadaserver.common.entity.Image;
import kr.anabada.anabadaserver.domain.recycle.dto.request.RecyclePostRequest;
import kr.anabada.anabadaserver.domain.recycle.dto.response.RecycleResponse;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;


@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "recycle")
@Where(clause = "is_removed = false")
@SQLDelete(sql = "UPDATE recycle SET is_removed = true WHERE id = ?")
public class Recycle extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @OneToOne
    @JoinColumn(name = "writer", nullable = false)
    private User writer;

    @Column(name = "content", length = 300)
    private String content;

    @Builder.Default
    @Column(name = "is_removed", nullable = false)
    private Boolean isRemoved = false;

    @BatchSize(size = 100)
    @JoinColumn(name = "post_id")
    @OneToMany(fetch = FetchType.LAZY)
    @Where(clause = "image_type = 'RECYCLE'")
    private List<Image> images;

    @Builder.Default
    @Column(name = "comment_cnt")
    private long commentCount = 0;

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void editPost(RecyclePostRequest recyclePostRequest) {
        if (StringUtils.hasText(recyclePostRequest.getTitle()))
            this.title = recyclePostRequest.getTitle();
        if (StringUtils.hasText(recyclePostRequest.getContent()))
            this.content = recyclePostRequest.getContent();
    }

    public RecycleResponse toResponse() {
        return RecycleResponse.builder()
                .id(this.id)
                .title(this.title)
                .content(this.content)
                .createdAt(getCreatedAt())
                .modifiedAt(getModifiedAt())
                .commentCount(this.commentCount)
                .writer(this.writer.toDto())
                .images(this.images.stream().map(Image::getId).map(UUID::toString).toList())
                .build();
    }

    public void increaseCommentCount() {
        this.commentCount++;
    }
}