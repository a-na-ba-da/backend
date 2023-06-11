package kr.anabada.anabadaserver.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
@EntityListeners(AuditingEntityListener.class)
public class Image {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "post_id")
    @org.hibernate.annotations.Comment("사용되는 타입 테이블의 id")
    private Long postId;

    @Column(name = "image_type", updatable = false)
    private String imageType;

    @Column(name = "ext", nullable = false, length = 5)
    private String extension;

    @Column(name = "original_file_name", length = 100)
    private String originalFileName;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "uploader", nullable = false)
    private Long uploader;

    public void attach(Long postId, long userId) {
        if (this.postId != null) {
            throw new IllegalArgumentException("이미 사용중인 이미지입니다.");
        }

        if (this.uploader != userId) {
            throw new IllegalArgumentException("해당 이미지는 다른 사용자가 업로드한 이미지입니다.");
        }

        this.postId = postId;
    }
}