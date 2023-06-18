package kr.anabada.anabadaserver.common.controller;

import jakarta.validation.constraints.NotNull;
import kr.anabada.anabadaserver.common.dto.NaverProductResponse;
import kr.anabada.anabadaserver.common.service.NaverProductService;
import kr.anabada.anabadaserver.global.exception.CustomException;
import kr.anabada.anabadaserver.global.exception.ErrorCode;
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
    public NaverProductResponse getItemList(@RequestParam @NotNull(message = "검색어를 입력해주세요.") String keyword) {
        NaverProductResponse result = naverProductService.searchProductByKeyword(keyword);
        if (result == null) {
            throw new CustomException(ErrorCode.NAVER_PRODUCT_API_FAILED);
        } else {
            return result;
        }
    }
}
