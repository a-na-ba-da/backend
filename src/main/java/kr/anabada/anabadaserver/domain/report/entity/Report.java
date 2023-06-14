package kr.anabada.anabadaserver.domain.report.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report")
@EntityListeners(AuditingEntityListener.class)
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("신고 타입")
    @Column(name = "report_type", nullable = false, length = 15)
    private String type;

    @Comment("신고 대상 PK (타입 테이블 내 index)")
    @Column(name = "report_target_id", nullable = false)
    private Long targetPK;

    @Comment("신고자 ID")
    @Column(name = "reporter_user_id", nullable = false)
    private Long reporter;

    @Comment("피신고자 ID")
    @Column(name = "reported_user_id", nullable = false)
    private Long reported;

    @Comment("신고 내용")
    @Column(name = "content", length = 500)
    private String content;

    @Builder.Default
    @Comment("신고 처리 여부")
    @Column(name = "confirmed")
    private Boolean confirmed = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}