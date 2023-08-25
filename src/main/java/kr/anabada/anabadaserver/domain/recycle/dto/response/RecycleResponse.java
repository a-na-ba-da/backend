package kr.anabada.anabadaserver.domain.recycle.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.anabada.anabadaserver.common.dto.LocalDateResponse;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@Schema(description = "다시쓰기 응답 값")
public class RecycleResponse extends LocalDateResponse {

    @Schema(description = "인덱스")
    private Long id;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "작성자")
    private UserDto writer;

    @Schema(description = "댓글 수 카운트")
    private long commentCount;

    @Schema(description = "이미지 리스트")
    private List<String> images;
}
