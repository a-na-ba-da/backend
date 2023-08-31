package kr.anabada.anabadaserver.common.entity;

import jakarta.persistence.*;
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

    public Image(UUID id, Long postId, String imageType, String extension, String originalFileName, LocalDateTime createdAt, Long uploader) {
        this.id = id;
        this.postId = postId;
        this.imageType = imageType;
        this.extension = extension;
        this.originalFileName = originalFileName;
        this.createdAt = createdAt;
        this.uploader = uploader;
    }

    public void attach(Long postId, long userId) {
        if (this.postId != null) {
            throw new IllegalStateException("이미 연결된 이미지입니다.");
        }

        if (this.uploader != userId) {
            throw new IllegalStateException("본인이 업로드한 이미지가 아닙니다.");
        }

        this.postId = postId;
    }

    public void setId(UUID uuid) {
        this.id = uuid;
    }
}