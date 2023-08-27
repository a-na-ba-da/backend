package kr.anabada.anabadaserver.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.DiscriminatorValue;
import kr.anabada.anabadaserver.domain.change.dto.ChangeRequestStatus;
import kr.anabada.anabadaserver.domain.change.entity.ChangeRequest;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MyActivityDto {
    List<SaveTypeRecord> saves = new ArrayList<>();
    List<PostTypeRecord> recycles = new ArrayList<>();
    List<ChangeRequestRecord> changes = new ArrayList<>();

    private MyActivityDto(List<SaveTypeRecord> saves, List<PostTypeRecord> recycles, List<ChangeRequestRecord> changes) {
        this.saves = saves;
        this.recycles = recycles;
        this.changes = changes;
    }

    static public MyActivityDto CreateMyActivityDto(List<Save> saves, List<Recycle> recycles, List<ChangeRequest> changes) {
        MyActivityDto myActivityDto = new MyActivityDto();
        for (Save save : saves) {
            myActivityDto.saves.add(
                    new SaveTypeRecord(
                            save.getId(),
                            save.getTitle(),
                            save.getContent(),
                            save.getClass().getAnnotation(DiscriminatorValue.class).value(),
                            save.getCreatedAt()));
        }

        for (Recycle recycle : recycles) {
            myActivityDto.recycles.add(new PostTypeRecord(recycle.getId(), recycle.getTitle(), recycle.getContent(), recycle.getCreatedAt()));
        }

        for (ChangeRequest change : changes) {
            myActivityDto.changes.add(new ChangeRequestRecord(change.getId(), change.getMessage(), change.getRejectMessage(), change.getStatus(), change.getCreatedAt()));
        }


        return myActivityDto;
    }

    public record SaveTypeRecord(
            long id,
            @Schema(description = "글 제목")
            String title,
            @Schema(description = "글 내용")
            String content,
            @Schema(description = "글 타입", example = "BUY_TOGETHER, KNOW_TOGETHER")
            String type,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @Schema(pattern = "yyyy-MM-dd HH:mm:ss", description = "생성일")
            LocalDateTime createdAt) {
    }

    public record PostTypeRecord(
            long id,
            @Schema(description = "글 제목")
            String title,
            @Schema(description = "글 내용")
            String content,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @Schema(pattern = "yyyy-MM-dd HH:mm:ss", description = "생성일")
            LocalDateTime createdAt) {
    }

    public record ChangeRequestRecord(
            long id,
            @Schema(description = "피신청자가 교환 요청과 함께 보낸 메세지", example = "저희 물건 교환해요!")
            String message,
            @Schema(description = "요청 상태가 RECJECTED일 경우, 피요청자가 보낸 거절 메세지", example = "물건이 마음에 안들어서 교환을 원하지 않습니다.")
            String rejectMessage,
            @Schema(description = "교환 요청 상태", example = "REQUESTING(피요청자의 승인여부를 기다리는 중), ACCEPTED(피요청자가 교환 승인함), REJECTED(피요청자에 의해 거절됨)")
            ChangeRequestStatus status,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            @Schema(pattern = "yyyy-MM-dd HH:mm:ss", description = "생성일")
            LocalDateTime createdAt) {
    }
}
