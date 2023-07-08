package kr.anabada.anabadaserver.domain.recycle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RecycleSaveResponseDto {
    private Long id;
    private String title;
    private Long writer;
    private String content;
    private LocalDateTime createdAt;

    @Builder
    public RecycleSaveResponseDto(Long id, String title, Long writer, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.createdAt = createdAt;
    }
}
