package kr.anabada.anabadaserver.domain.share.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.anabada.anabadaserver.common.dto.LocalDateResponse;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@SuperBuilder
@Schema(description = "나눠쓰기 응답 값")
public class LendResponse extends LocalDateResponse {
    @Schema(description = "인덱스")
    private Long id;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "작성자")
    private UserDto writer;

    @Schema(description = "대여 시작 일자")
    private LocalDate start;

    @Schema(description = "대여 종료 일자")
    private LocalDate end;

    @Schema(description = "대여 장소 위도")
    private Double lat;

    @Schema(description = "대여 장소 경도")
    private Double lng;

    @Schema(description = "일일 대여 금액")
    private Long pricePerDay;

    @Schema(description = "댓글 수 카운트")
    private long commentCount;

    @Schema(description = "이미지 리스트")
    private List<String> images;
}
