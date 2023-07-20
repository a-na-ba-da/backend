package kr.anabada.anabadaserver.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotNull;
import kr.anabada.anabadaserver.common.dto.NaverProductResponse;
import kr.anabada.anabadaserver.common.service.NaverProductService;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import kr.anabada.anabadaserver.global.response.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/third-party/naver-product")
public class NaverProductController {
    private final NaverProductService naverProductService;

    @GetMapping("")
    @Operation(
            summary = "네이버 상품 검색 API",
            description = "키워드를 통해 네이버 스토어에서 상품을 검색합니다. <br/>" +
                    "성공하면 검색된 상품들을 반환합니다."
    )
    @ApiResponse(
            responseCode = "200", description = "네이버 상품 검색 성공"
    )
    public GlobalResponse<NaverProductResponse> getItemList(
            @Parameter(description = "네이버 상품에서 검색 할 상품의 검색어", required = true)
            @RequestParam @NotNull(message = "검색어를 입력해주세요.")
            String keyword) {
        NaverProductResponse result = naverProductService.searchProductByKeyword(keyword);
        if (result == null) {
            throw new CustomException(ErrorCode.NAVER_PRODUCT_API_FAILED);
        }

        return new GlobalResponse<>(result);
    }
}
