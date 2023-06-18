package kr.anabada.anabadaserver.domain.change.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@Schema(description = "바꿔쓰기 내 상품 생성")
public class MyProductRequest {
    @NotNull(message = "네이버 상품 id값을 입력해주세요.")
    @Schema(description = "네이버 상품 id값", example = "34563199618")
    private Long naverProductId;

    @NotNull(message = "상품 설명을 입력해주세요.")
    @Size(min = 1, max = 300, message = "상품 설명은 1자 이상 300자 이하로 작성해주세요.")
    private String content;

    @NotNull(message = "이미지를 업로드 해주세요.")
    @Size(min = 1, max = 5, message = "이미지는 1개 이상 5개 이하로 업로드 해주세요.")
    @Schema(description = "사용자가 직접 업로드한 상품 이미지 파일 이름 Array", example = "[\"6d4ca57f-71c6-4886-b464-6d2847b3ebb8\", \"e490da47-5e19-44ac-8565-448600d870fd\"]")
    private List<String> images;
}
