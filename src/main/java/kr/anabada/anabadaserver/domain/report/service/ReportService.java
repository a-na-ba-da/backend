package kr.anabada.anabadaserver.domain.report.service;

import kr.anabada.anabadaserver.domain.report.dto.ReportDto;
import kr.anabada.anabadaserver.domain.report.entity.Report;
import kr.anabada.anabadaserver.domain.report.repository.ReportRepository;
import kr.anabada.anabadaserver.domain.save.repository.SaveRepository;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {
    private final SaveRepository saveRepository;
    private final ReportRepository reportRepository;

    @Transactional
    public void report(long reporterId, ReportDto reportDto) {
        ReportableRecord reportableRecord =
                switch (reportDto.getType()) {
                    case BUY_TOGETHER, KNOW_TOGETHER -> chkSaveReportable(reporterId, reportDto);
                    default -> throw new IllegalArgumentException("잘못된 신고 타입입니다.");
                };

        if (checkDuplicateReport(reportableRecord.reported, reporterId, reportDto)) {
            throw new CustomException(ErrorCode.DUPLICATE_REPORT);
        }

        saveReport(reporterId, reportDto, reportableRecord);
    }

    private ReportableRecord chkSaveReportable(long reporterId, ReportDto reportDto) {
        Long targetPostWriterId = saveRepository.getPostWriterNotMine(reporterId, reportDto.getPostId());
        if (targetPostWriterId == null) {
            throw new CustomException(ErrorCode.CANT_REPORT);
        }

        return new ReportableRecord(targetPostWriterId, reportDto.getPostId());
    }

    private void saveReport(long reporterId, ReportDto reportDto, ReportableRecord reportableRecord) {
        if (reportableRecord.reported == reporterId) {
            // 한번 더 체크
            throw new CustomException(ErrorCode.CANT_REPORT);
        }

        Report report = Report.builder()
                .type(reportDto.getType().toString())
                .targetPK(reportableRecord.targetPK)
                .reporter(reporterId)
                .reported(reportableRecord.reported)
                .build();
        reportRepository.save(report);
    }

    private boolean checkDuplicateReport(long reportedId, long reporterId, ReportDto reportDto) {
        return reportRepository.ChkDupReport(reportDto.getType().toString(), reportDto.getPostId(), reporterId, reportedId);
    }

    private record ReportableRecord(
            Long reported,
            Long targetPK
    ) {
    }
}
