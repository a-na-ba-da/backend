package kr.anabada.anabadaserver.domain.change.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Schema(description = "상품 조회 응답")
public class MyProductResponse {
    @Schema(description = "상품 id", example = "1")
    private Long id;

    @Schema(description = "상품 소유자 정보")
    private UserDto owner;

    @Schema(description = "상품 이름", example = "이스트라 AN753UHD 스마트 THE 울트라")
    private String name;

    @Schema(description = "상품 설명", example = "상품 설명입니다.")
    private String content;

    @Schema(description = "상품 가격", example = "10000")
    private int originalPrice;

    @Schema(description = "상품 상태", example = "AVAILABLE")
    private ProductStatus status;

    @Schema(description = "상품 대표 이미지", example = "6d4ca57f-71c6-4886-b464-6d2847b3ebb8")
    private UUID image;
}