package kr.anabada.anabadaserver.domain.recycle.dto;

import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecycleDto {
    private Long id;
    private String title;
    private Long writer;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Boolean isRemoved;
}
