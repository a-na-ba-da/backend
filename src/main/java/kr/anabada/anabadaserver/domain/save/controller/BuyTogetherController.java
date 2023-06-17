package kr.anabada.anabadaserver.domain.save.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import kr.anabada.anabadaserver.domain.save.dto.BuyTogetherDto;
import kr.anabada.anabadaserver.domain.save.dto.SaveSearchRequestDto;
import kr.anabada.anabadaserver.domain.save.service.BuyTogetherService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.exception.CustomException;
import kr.anabada.anabadaserver.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/saving/buy-together")
@Tag(name = "아껴쓰기", description = "같이 사요, 같이 알아요 API 모음")
public class BuyTogetherController {
    private final BuyTogetherService buyTogetherService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "같이사요 생성 성공")
    public void createNewBuyTogetherPost(@CurrentUser User user, @Validated BuyTogetherDto buyTogetherDto) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        buyTogetherDto.validate();
        buyTogetherService.createNewBuyTogetherPost(user, buyTogetherDto);
    }

    @GetMapping("")
    @ApiResponse(responseCode = "200", description = "같이사요 목록 조회 성공")
    public Page<BuyTogetherDto> getBuyTogetherList(Pageable pageable, SaveSearchRequestDto searchRequest) {
        List<BuyTogetherDto> result = buyTogetherService.getBuyTogetherList(searchRequest, pageable);
        return new PageImpl<>(result, pageable, result.size());
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "같이사요 단건 조회 성공")
    public BuyTogetherDto getBuyTogether(@PathVariable @NotNull(message = "게시물 id를 입력해주세요.") Long id) {
        return buyTogetherService.getBuyTogether(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "204", description = "본인이 작성한 같이사요 글 삭제 성공")
    public void deleteBuyTogether(@CurrentUser User user, @PathVariable @NotNull(message = "삭제할 게시물 id가 없습니다.") Long id) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        buyTogetherService.removeMyPost(user, id);
    }
}
