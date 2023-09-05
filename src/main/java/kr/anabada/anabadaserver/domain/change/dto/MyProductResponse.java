package kr.anabada.anabadaserver.domain.change.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "상품 조회 응답")
public abstract class MyProductResponse {

    @Schema(description = "상품 id", example = "1")
    private Long id;

    @Schema(description = "상품 이름", example = "이스트라 AN753UHD 스마트 THE 울트라")
    private String name;

    @Schema(description = "상품 설명", example = "상품 설명입니다.")
    private String content;

    @Schema(description = "상품 가격", example = "10000")
    private int originalPrice;

    @Schema(description = "상품 상태", example = "AVAILABLE")
    private ProductStatus status;

    @Schema(description = "상품 이미지 파일 이름")
    private List<String> images;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    private MyProductResponse(Long id, String name, String content, int originalPrice, ProductStatus status, List<String> images, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.originalPrice = originalPrice;
        this.status = status;
        this.images = images;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    @Getter
    public static class IncludeOwner extends MyProductResponse {
        @Schema(description = "상품 소유자 정보")
        private final UserDto owner;

        public IncludeOwner(Long id, String name, String content, int originalPrice, ProductStatus status, List<String> images, UserDto owner, LocalDateTime createdAt, LocalDateTime modifiedAt) {
            super(id, name, content, originalPrice, status, images, createdAt, modifiedAt);
            this.owner = owner;
        }
    }

    public static class ExcludeOwner extends MyProductResponse {
        public ExcludeOwner(Long id, String name, String content, int originalPrice, ProductStatus status, List<String> images, LocalDateTime createdAt, LocalDateTime modifiedAt) {
            super(id, name, content, originalPrice, status, images, createdAt, modifiedAt);
        }
    }
}
