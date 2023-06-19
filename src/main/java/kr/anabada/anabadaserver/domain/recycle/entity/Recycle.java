package kr.anabada.anabadaserver.domain.recycle.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.domain.recycle.dto.RecycleDto;
import lombok.AllArgsConstructor;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recycle")
@Where(clause = "isRemoved = false")
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


    public RecycleDto toDto() {
        return RecycleDto.builder()
                .id(getId())
                .title(getTitle())
                .writer(getWriter())
                .content(getContent())
                .createdAt(getCreatedAt())
                .modifiedAt(getModifiedAt())
                .isRemoved(getIsRemoved())
                .build();
    }
}