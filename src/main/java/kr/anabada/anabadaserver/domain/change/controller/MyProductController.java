package kr.anabada.anabadaserver.domain.change.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.anabada.anabadaserver.domain.change.dto.MyProductRequest;
import kr.anabada.anabadaserver.domain.change.dto.MyProductResponse;
import kr.anabada.anabadaserver.domain.change.service.MyProductService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.exception.CustomException;
import kr.anabada.anabadaserver.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/changing")
@Tag(name = "바꿔쓰기", description = "바꿔쓰기 관련 API")
public class MyProductController {
    private final MyProductService myProductService;

    @Operation(summary = "바꿔쓰기에 사용할 내 상품 등록")
    public void createMyProduct(@CurrentUser User user, @Valid MyProductRequest request) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        myProductService.createMyProduct(user, request);
    }

    @Operation(summary = "내 상품 목록 조회")
    public Page<MyProductResponse> getMyProduct(@CurrentUser User user, String searchWord, Pageable pageable) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        if (StringUtils.hasText(searchWord) && searchWord.trim().length() < 2)
            throw new CustomException(ErrorCode.SEARCH_WORD_LENGTH);


        return myProductService.getMyProduct(user, searchWord, pageable);
    }

}
