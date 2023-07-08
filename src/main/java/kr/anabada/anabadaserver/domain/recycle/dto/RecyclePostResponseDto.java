package kr.anabada.anabadaserver.domain.recycle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.ErrorResponse;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RecyclePostResponseDto {
    private Long id;
    private String title;
    private Long writer;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    public RecyclePostResponseDto(Long id, String title, Long writer, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
