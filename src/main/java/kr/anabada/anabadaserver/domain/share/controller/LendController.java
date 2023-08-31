package kr.anabada.anabadaserver.domain.share.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.anabada.anabadaserver.domain.share.dto.request.LendPostRequest;
import kr.anabada.anabadaserver.domain.share.dto.response.LendResponse;
import kr.anabada.anabadaserver.domain.share.entity.Lend;
import kr.anabada.anabadaserver.domain.share.service.LendService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import kr.anabada.anabadaserver.global.response.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lending")
@Tag(name = "나눠쓰기", description = "나눠쓰기 API 모음")
public class LendController {

    private final LendService lendService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "나눠쓰기 생성 성공")
    public void createNewLending(@CurrentUser User user, @RequestBody @Valid LendPostRequest lendPostRequest) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        lendService.createNewLendPost(user, lendPostRequest);
    }

    @PatchMapping("/{lendId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "201", description = "나눠쓰기 수정 성공")
    public void modifyLendPost(@CurrentUser User user, @PathVariable @NotNull(message = "게시물 id를 입력해주세요.") Long lendId, @RequestBody @Valid LendPostRequest lendPostRequest) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        lendService.modifyLendPost(user, lendId, lendPostRequest);
    }

    @DeleteMapping("/{lendId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "204", description = "나눠쓰기 삭제 성공")
    public void deleteLendPost(@CurrentUser User user, @PathVariable @NotNull(message = "게시물 id를 입력해주세요.") Long lendId) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        lendService.deleteLendPost(user, lendId);
    }

    @GetMapping("")
    @ApiResponse(responseCode = "200", description = "나눠쓰기 목록 조회 성공")
    public GlobalResponse<PageImpl<LendResponse>> getLendList(@ParameterObject Pageable pageable) {
        List<LendResponse> lendList = lendService.getLendList(pageable)
                .stream().map(Lend::toResponse).toList();

        return new GlobalResponse<>(new PageImpl<>(lendList, pageable, lendList.size()));
    }

    @GetMapping("/{lendId}")
    @ApiResponse(responseCode = "200", description = "나눠쓰기 단건 조회 성공")
    public GlobalResponse<LendResponse> getLend(@PathVariable @NotNull(message = "게시물 id를 입력해주세요.") Long lendId) {
        return new GlobalResponse<>(lendService.getLend(lendId).toResponse());
    }
}
