package kr.anabada.anabadaserver.domain.change.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.anabada.anabadaserver.domain.change.service.ProductChangeService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
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

    @PostMapping("/{targetProductId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createChangeRequest(@CurrentUser User user, @PathVariable Long targetProductId, List<Long> myProductIds,
                                    @NotNull(message = "메시지를 입력해주세요.") @Length(max = 30, message = "메세지는 최대 30자 까지 입력가능합니다.") String message) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        productChangeService.changeRequest(user, targetProductId, myProductIds, message);
    }
}
