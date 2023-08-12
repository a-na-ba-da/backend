package kr.anabada.anabadaserver.domain.recycle.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "recycle")
@Where(clause = "is_removed = false")
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE recycle SET is_removed = true WHERE id = ?")
public class Recycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "writer", nullable = false)
    private Long writer;

    @Column(name = "content", length = 300)
    private String content;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Builder.Default
    @Column(name = "is_removed", nullable = false)
    private Boolean isRemoved = false;


    @Builder
    public Recycle(Long id, String title, Long writer, String content, LocalDateTime createdAt, LocalDateTime modifiedAt, Boolean isRemoved) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.isRemoved = isRemoved;
    }

    public void setWriter(User writer) {
        this.writer = writer.getId();
    }
}