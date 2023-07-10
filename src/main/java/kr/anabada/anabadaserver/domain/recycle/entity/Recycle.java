package kr.anabada.anabadaserver.domain.recycle.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.common.entity.BaseTimeEntity;
import kr.anabada.anabadaserver.domain.recycle.dto.RecyclePostResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

@Getter
@Entity
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

    @Column(name = "writer", nullable = false)
    private Long writer;

    @Column(name = "content", length = 300)
    private String content;

    @Column(name = "is_removed", nullable = false)
    private Boolean isRemoved = false;

    @Builder
    public Recycle(Long id, String title, Long writer, String content, Boolean isRemoved) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.isRemoved = isRemoved;
    }


    public RecyclePostResponseDto toDto() {
        return RecyclePostResponseDto.builder()
                .title(getTitle())
                .content(getContent())
                .build();
    }

    public void editPost(String title, String content) {
        if (StringUtils.hasText(title))
            this.title = title;

        if (StringUtils.hasText(content))
            this.title = content;
    }
}
