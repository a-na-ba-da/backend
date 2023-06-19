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
    private Long id;
    private UserDto owner;
    private String name;
    private String content;
    private int originalPrice;
    private ProductStatus status;
    private UUID image;
}
