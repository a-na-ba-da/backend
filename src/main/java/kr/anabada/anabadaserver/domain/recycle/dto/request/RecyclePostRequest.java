package kr.anabada.anabadaserver.domain.recycle.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@NoArgsConstructor
public class RecyclePostRequest {

    @Schema(description = "제목")
    @Length(min = 2, max = 30, message = "제목은 2자 이상 30자 이하로 작성해주세요.")
    private String title;

    @Schema(description = "내용")
    @Length(min = 5, max = 700, message = "내용은 5자 이상 700자 이하로 작성해주세요.")
    private String content;

    @Schema(description = "이미지 파일명 리스트")
    @Size(min = 1, max = 5, message = "이미지는 1개 이상 5개 이하로 업로드 해주세요.")
    private List<String> images;

    @Builder
    public RecyclePostRequest(String title, String content, List<String> images) {
        this.title = title;
        this.content = content;
        this.images = images;
    }

    public Recycle toEntity(User writer, RecyclePostRequest recyclePostRequest) {
        return Recycle.builder()
                .title(recyclePostRequest.getTitle())
                .content(recyclePostRequest.getContent())
                .writer(writer)
                .build();
    }

    public void checkValidation(){
        if(images == null || images.isEmpty()){
            throw new CustomException(ErrorCode.NOT_EXIST_IMAGE);
        }
    }
}
