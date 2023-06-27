package kr.anabada.anabadaserver.common.controller;

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
    public GlobalResponse<NaverProductResponse> getItemList(@RequestParam @NotNull(message = "검색어를 입력해주세요.") String keyword) {
        NaverProductResponse result = naverProductService.searchProductByKeyword(keyword);
        if (result == null) {
            throw new CustomException(ErrorCode.NAVER_PRODUCT_API_FAILED);
        }
        
        return new GlobalResponse<>(result);
    }
}
