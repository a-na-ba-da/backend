package kr.anabada.anabadaserver.domain.report.dto;

import jakarta.validation.constraints.NotNull;
import kr.anabada.anabadaserver.common.dto.DomainType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReportDto {
    @NotNull(message = "신고 타입을 입력해주세요")
    DomainType type;
    @NotNull(message = "신고 대상을 입력해주세요")
    Long postId;
    @NotNull(message = "신고 내용을 입력해주세요")
    String message;
}
