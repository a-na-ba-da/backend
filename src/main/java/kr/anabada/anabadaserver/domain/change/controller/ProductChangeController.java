package kr.anabada.anabadaserver.domain.change.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import kr.anabada.anabadaserver.domain.change.dto.ChangeRequestResponse;
import kr.anabada.anabadaserver.domain.change.service.ProductChangeService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import kr.anabada.anabadaserver.global.response.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/changing")
@Tag(name = "바꿔쓰기", description = "바꿔쓰기 관련 API")
public class ProductChangeController {

    private final ProductChangeService productChangeService;

    @GetMapping("")
    @Operation(
            summary = "내가 요청 혹은 요청받은 바꿔쓰기 전부 조회"
    )
    public GlobalResponse<ChangeRequestResponse> getMyChangingRequest(@CurrentUser User user) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        return new GlobalResponse<>(productChangeService.getAllChangeRequest(user));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{targetProductId}")
    @Operation(
            summary = "바꿔쓰기 요청 생성"
    )
    public void createChangeRequest(@CurrentUser User user,
                                    @Schema(description = "바꿔쓰기 요청을 받을 상품의 ID (변경대상)", required = true, example = "1")
                                    @PathVariable Long targetProductId,
                                    @Schema(description = "바꿔쓰기 요청을 보낼 상품들의 ID 리스트 (변경요청)", required = true, example = "[2, 3]")
                                    List<Long> myProductIds,
                                    @Schema(description = "바꿔쓰기 요청 메시지", example = "강남역에서 트레이드 함 하실래요? A급 상품들입니다.")
                                    @NotNull(message = "메시지를 입력해주세요.") @Length(max = 30, message = "메세지는 최대 30자 까지 입력가능합니다.") String message) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        productChangeService.changeRequest(user, targetProductId, myProductIds, message);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/accept/{changeRequestId}")
    @Operation(
            summary = "바꿔쓰기 요청 수락"
    )
    public void acceptChangeRequest(@CurrentUser User user,
                                    @Schema(description = "바꿔쓰기 요청 ID", required = true, example = "1")
                                    @PathVariable Long changeRequestId) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);
        if (changeRequestId == null)
            throw new IllegalArgumentException("changeRequestId를 입력해주세요.");

        productChangeService.acceptChangeRequest(user, changeRequestId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/reject/{changeRequestId}")
    @Operation(
            summary = "바꿔쓰기 요청 거절"
    )
    public void rejectChangeRequest(@CurrentUser User user,
                                    @Schema(description = "바꿔쓰기 요청 ID", required = true, example = "1")
                                    @PathVariable Long changeRequestId,
                                    @RequestBody String rejectMessage) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);
        if (changeRequestId == null)
            throw new IllegalArgumentException("changeRequestId를 입력해주세요.");
        if (StringUtils.hasText(rejectMessage))
            throw new IllegalArgumentException("rejectMessage를 입력해주세요.");

        productChangeService.rejectChangeRequest(user, changeRequestId, rejectMessage);
    }
}
