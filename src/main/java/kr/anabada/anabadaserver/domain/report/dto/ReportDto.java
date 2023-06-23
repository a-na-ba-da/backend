package kr.anabada.anabadaserver.domain.report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import kr.anabada.anabadaserver.common.dto.DomainType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReportDto {
    @Schema(description = "신고 타입", example = "BUY_TOGETHER")
    @NotNull(message = "신고 타입을 입력해주세요")
    DomainType type;

    @Schema(description = "신고 대상 포스트 혹은 메세지의 id", example = "1")
    @NotNull(message = "신고 대상을 입력해주세요")
    Long postId;
    
    @Schema(description = "신고 내용", example = "이 사람이 불쾌해요...")
    @NotNull(message = "신고 내용을 입력해주세요")
    String message;
}
