package kr.anabada.anabadaserver.domain.report.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.anabada.anabadaserver.domain.report.dto.ReportDto;
import kr.anabada.anabadaserver.domain.report.service.ReportService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/report")
@Tag(name = "common", description = "각 도메인에서 공통으로 사용되는 API")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("")
    @ResponseStatus(value = CREATED)
    @Operation(summary = "신고", description = "여러 도메인에서 발생한 사람 간 문제에 대해 신고를 처리하는 API")
    @ApiResponse(responseCode = "201", description = "신고 성공")
    public void report(@CurrentUser User user, @Valid ReportDto reportDto) {
        if (user == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        reportService.report(user.getId(), reportDto);
    }

}
