package kr.anabada.anabadaserver.domain.change.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/changing")
@Tag(name = "바꿔쓰기", description = "바꿔쓰기 관련 API")
public class ProductChangeController {

    private final ProductChangeService productChangeService;

    @GetMapping("")
    @Operation(summary = "내가 요청 혹은 요청받은 바꿔쓰기 전부 조회")
    public GlobalResponse<ChangeRequestResponse> getMyChangingRequest(@CurrentUser User user) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        return new GlobalResponse<>(productChangeService.getAllChangeRequest(user));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{targetProductId}")
    @Operation(summary = "바꿔쓰기 요청 생성")
    public void createChangeRequest(@CurrentUser User user, @PathVariable Long targetProductId, List<Long> myProductIds,
                                    @NotNull(message = "메시지를 입력해주세요.") @Length(max = 30, message = "메세지는 최대 30자 까지 입력가능합니다.") String message) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        productChangeService.changeRequest(user, targetProductId, myProductIds, message);
    }
}
