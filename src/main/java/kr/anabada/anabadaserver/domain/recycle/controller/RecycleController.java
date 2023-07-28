package kr.anabada.anabadaserver.domain.recycle.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.anabada.anabadaserver.domain.recycle.dto.request.RecyclePostRequestDto;
import kr.anabada.anabadaserver.domain.recycle.service.RecycleService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recycle")
@Tag(name = "다시쓰기", description = "다시쓰기 API 모음")
public class RecycleController {
    private final RecycleService recycleService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "다시쓰기 생성 성공")
    public void createNewRecyclePost(@CurrentUser User user, @RequestBody @Valid RecyclePostRequestDto recyclePostRequestDto){
        if(user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        recycleService.createNewRecyclePost(user, recyclePostRequestDto);

    }

}
