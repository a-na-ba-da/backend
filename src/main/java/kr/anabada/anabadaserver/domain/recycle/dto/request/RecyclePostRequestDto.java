package kr.anabada.anabadaserver.domain.recycle.dto.request;

import jakarta.validation.constraints.NotNull;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class RecyclePostRequestDto {
    @NotNull(message = "게시글 제목을 입력해주세요.")
    private String title;

    @NotNull(message = "게시글 내용을 입력해주세요.")
    private String content;

    public RecyclePostRequestDto(@NotNull(message = "게시글 제목을 입력해주세요.") String title, @NotNull(message = "게시글 내용을 입력해주세요.") String content) {
        this.title = title;
        this.content = content;
    }
    
    @Builder
    public Recycle toEntity(User writer, RecyclePostRequestDto recyclePostRequestDto) {
        return Recycle.builder()
                .title(recyclePostRequestDto.getTitle())
                .content(recyclePostRequestDto.getContent())
                .writer(writer.getId())
                .build();
    }
}
