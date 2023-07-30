package kr.anabada.anabadaserver.domain.save.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.anabada.anabadaserver.domain.save.dto.request.BuyTogetherMeetRequest;
import kr.anabada.anabadaserver.domain.save.dto.request.BuyTogetherParcelRequest;
import kr.anabada.anabadaserver.domain.save.dto.request.SaveSearchRequestDto;
import kr.anabada.anabadaserver.domain.save.dto.response.BuyTogetherResponse;
import kr.anabada.anabadaserver.domain.save.entity.BuyTogether;
import kr.anabada.anabadaserver.domain.save.service.BuyTogetherService;
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
@RequestMapping("/api/v1/saving/buy-together")
@Tag(name = "아껴쓰기", description = "같이 사요, 같이 알아요 API 모음")
public class BuyTogetherController {
    private final BuyTogetherService buyTogetherService;

    @PostMapping("/parcel-delivery")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "같이사요 생성 성공")
    @Operation(summary = "같이사요 생성 - case 1", description = "물건 산 이후 택배로 전달하는 같이사요 게시물 생성")
    public void createNewBuyTogetherParcel(@CurrentUser User user, @RequestBody @Valid BuyTogetherParcelRequest request) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        request.checkValidation();
        buyTogetherService.createNewBuyTogetherPost(user, request);
    }

    @PostMapping("/meet-delivery")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "같이사요 생성 성공")
    @Operation(summary = "같이사요 생성 - case 2", description = "물건 산 이후 대면으로 전달하는 같이사요 게시물 생성")
    public void createNewBuyTogetherMeet(@CurrentUser User user, @RequestBody @Valid BuyTogetherMeetRequest request) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        request.checkValidation();
        buyTogetherService.createNewBuyTogetherPost(user, request);
    }

    @GetMapping("")
    @ApiResponse(responseCode = "200", description = "같이사요 목록 조회 성공")
    public GlobalResponse<PageImpl<BuyTogetherResponse>> getBuyTogetherList(@ParameterObject Pageable pageable,
                                                                            @ParameterObject SaveSearchRequestDto searchRequest) {
        List<BuyTogetherResponse> result = buyTogetherService.getBuyTogetherList(searchRequest, pageable)
                .stream().map(BuyTogether::toResponse).toList();
        return new GlobalResponse<>(new PageImpl<>(result, pageable, result.size()));
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "같이사요 단건 조회 성공")
    public GlobalResponse<BuyTogetherResponse> getBuyTogether(@PathVariable @NotNull(message = "게시물 id를 입력해주세요.") Long id) {
        return new GlobalResponse<>(buyTogetherService.getPost(id).toResponse());
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
