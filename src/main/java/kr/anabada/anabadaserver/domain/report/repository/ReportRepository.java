package kr.anabada.anabadaserver.domain.report.repository;

import kr.anabada.anabadaserver.domain.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends JpaRepository<Report, Long> {
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Report r " +
            "WHERE r.type = :type AND r.targetPK = :targetPK AND r.reporter = :reporter AND r.reported = :reported")
    boolean ChkDupReport(@Param("type") String type, @Param("targetPK") Long targetPK,
                         @Param("reporter") Long reporter, @Param("reported") Long reported);
}
