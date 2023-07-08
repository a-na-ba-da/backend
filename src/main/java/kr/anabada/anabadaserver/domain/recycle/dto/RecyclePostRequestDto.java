package kr.anabada.anabadaserver.domain.recycle.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RecyclePostRequestDto {
    @NotNull(message = "게시글 제목을 입력해주세요.")
    private String title;

    @NotNull(message = "게시글 내용을 입력해주세요.")
    private String content;

}
