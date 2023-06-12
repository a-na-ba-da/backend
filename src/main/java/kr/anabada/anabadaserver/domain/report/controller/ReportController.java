package kr.anabada.anabadaserver.domain.report.controller;

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
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("")
    @ResponseStatus(value = CREATED)
    public void report(@CurrentUser User user, @Valid ReportDto reportDto) {
        if (user == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        reportService.report(user.getId(), reportDto);
    }

}
