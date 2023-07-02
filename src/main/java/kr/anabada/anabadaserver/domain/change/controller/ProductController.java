package kr.anabada.anabadaserver.domain.change.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.anabada.anabadaserver.domain.change.dto.MyProductRequest;
import kr.anabada.anabadaserver.domain.change.dto.MyProductResponse;
import kr.anabada.anabadaserver.domain.change.service.ProductService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import kr.anabada.anabadaserver.global.response.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/changing")
@Tag(name = "바꿔쓰기", description = "바꿔쓰기 관련 API")
public class ProductController {
    private final ProductService myProductService;

    @PostMapping("/my-product")
    @Operation(summary = "바꿔쓰기에 사용할 내 상품 등록")
    public void createMyProduct(@CurrentUser User user, @RequestBody @Valid MyProductRequest request) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        myProductService.createMyProduct(user, request);
    }

    @GetMapping("/my-product")
    @Operation(summary = "내 상품 목록 조회")
    public GlobalResponse<Page<MyProductResponse>> getMyProduct(@CurrentUser User user,
                                                                @Parameter(description = "검색어 없다면 요청시 제외 할것")
                                                                String searchWord,
                                                                Pageable pageable,
                                                                @Parameter(description = "true: 전체 물건 조회, false: AVAILABLE 물건만 조회")
                                                                boolean searchAllStatus) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        if (isInvalidSearchWord(searchWord))
            throw new CustomException(ErrorCode.SEARCH_WORD_LENGTH);

        return new GlobalResponse<>(myProductService.getMyProducts(user, searchWord, pageable, searchAllStatus));
    }

    @GetMapping("/product")
    @Operation(summary = "모든(내 상품 포함) 상품 목록 조회 - (AVAILABLE 상품만 조회)")
    public GlobalResponse<Page<MyProductResponse>> getAllProduct(String searchWord, Pageable pageable) {
        if (isInvalidSearchWord(searchWord))
            throw new CustomException(ErrorCode.SEARCH_WORD_LENGTH);

        return new GlobalResponse<>(myProductService.getProducts(searchWord, pageable));
    }

    private boolean isInvalidSearchWord(String searchWord) {
        return StringUtils.hasText(searchWord) && searchWord.trim().length() < 2;
    }
}
