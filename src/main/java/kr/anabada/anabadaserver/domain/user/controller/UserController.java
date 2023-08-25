package kr.anabada.anabadaserver.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kr.anabada.anabadaserver.domain.user.dto.UserInfoChangeRequest;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.domain.user.service.UserService;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @PatchMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "내 정보 변경 성공")
    @Operation(summary = "내 정보 변경", description = "아직은 닉네임 변경밖에 없음")
    public void changeMyInformation(@CurrentUser User user, UserInfoChangeRequest request) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        userService.changeMyInformation(user, request);
    }


    // todo 내 활동 내역 확인
    @GetMapping("/activity")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(responseCode = "200", description = "내 활동 내역 조회 성공")
    @Operation(summary = "내 활동 내역 조회", description = "내가 작성한 글들을 확인 할 수 있음")
    public void getMyActivity(@CurrentUser User user) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        userService.getMyActivity(user);
    }
}
