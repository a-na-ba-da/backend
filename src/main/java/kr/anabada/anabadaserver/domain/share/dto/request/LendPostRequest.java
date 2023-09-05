package kr.anabada.anabadaserver.domain.share.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import kr.anabada.anabadaserver.domain.share.entity.Lend;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "나눠쓰기 생성 request")
public class LendPostRequest {

    @Schema(description = "제목")
    @Length(min = 2, max = 30, message = "제목은 2자 이상 30자 이하로 작성해주세요.")
    private String title;

    @Schema(description = "내용")
    @Length(min = 5, max = 700, message = "내용은 5자 이상 700자 이하로 작성해주세요.")
    private String content;

    @Schema(description = "대여 시작 일자")
    @NotNull(message = "대여 시작 일자를 입력해주세요. (형식: yyyy-MM-dd)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate start;

    @Schema(description = "대여 종료 일자")
    @NotNull(message = "대여 종료 일자를 입력해주세요. (형식: yyyy-MM-dd)")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;

    @Schema(description = "일일 대여 금액")
    @Positive(message = "일일 대여 금액은 0원보다 커야합니다.")
    private Long pricePerDay;


    @Schema(description = "대여 장소 위도")
    @Range(min = -90, max = 90, message = "위도의 범위는 -90 ~ 90 입니다.")
    private Double lat;

    @Schema(description = "대여 장소 경도")
    @Range(min = -180, max = 180, message = "경도의 범위는 -180 ~ 180 입니다.")
    private Double lng;

    @Schema(description = "이미지 파일명 리스트")
    @Size(min = 1, max = 5, message = "이미지는 1개 이상 5개 이하로 업로드 해주세요.")
    private List<String> images;

    @Builder
    public LendPostRequest(String title, String content, @NotNull(message = "대여 시작 일자를 입력해주세요. (형식: yyyy-MM-dd)") LocalDate start, @NotNull(message = "대여 종료 일자를 입력해주세요. (형식: yyyy-MM-dd)") LocalDate end, Long pricePerDay, Double lat, Double lng, List<String> images) {
        this.title = title;
        this.content = content;
        this.start = start;
        this.end = end;
        this.pricePerDay = pricePerDay;
        this.lat = lat;
        this.lng = lng;
        this.images = images;
    }

    public Lend toEntity(User writer, LendPostRequest lendPostRequest) {
        return Lend.builder()
                .title(lendPostRequest.getTitle())
                .content(lendPostRequest.getContent())
                .writer(writer)
                .start(lendPostRequest.getStart())
                .end(lendPostRequest.getEnd())
                .pricePerDay(lendPostRequest.getPricePerDay())
                .lat(lendPostRequest.getLat())
                .lng(lendPostRequest.getLng())
                .build();
    }

    public void checkValidation() {
        if (images == null || images.isEmpty())
            throw new CustomException(ErrorCode.NOT_EXIST_IMAGE);

        if (lat == null || lng == null)
            throw new IllegalArgumentException("물건을 대여할 인근 위치를 입력해주세요.");

        if (pricePerDay == null || pricePerDay <= 0)
            throw new IllegalArgumentException("일일 대여 금액은 0 보다 커야합니다.");

        if (start.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("대여 시작일은 현재보다 이후로 설정해주세요.");

        if (end.isBefore(start))
            throw new IllegalArgumentException("대여 종료일은 대여 시작일 이후로 설정해주세요.");
    }
}
