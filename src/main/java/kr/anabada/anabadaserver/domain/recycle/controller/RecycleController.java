package kr.anabada.anabadaserver.domain.recycle.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.anabada.anabadaserver.domain.recycle.dto.request.RecyclePostRequest;
import kr.anabada.anabadaserver.domain.recycle.dto.response.RecycleResponse;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.recycle.service.RecycleService;
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
@RequestMapping("/api/v1/recycle")
@Tag(name = "다시쓰기", description = "다시쓰기 API 모음")
public class RecycleController {
    private final RecycleService recycleService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "다시쓰기 생성 성공")
    public void createNewRecyclePost(@CurrentUser User user, @RequestBody @Valid RecyclePostRequest recyclePostRequest){
        if(user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        recycleService.createNewRecyclePost(user, recyclePostRequest);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "201", description = "다시쓰기 수정 성공")
    public void modifyRecyclePost(@CurrentUser User user, Long recycleId, @RequestBody @Valid RecyclePostRequest recyclePostRequest) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        recycleService.modifyRecyclePost(user, recycleId, recyclePostRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "204", description = "다시쓰기 삭제 성공")
    public void deleteRecycelPost(@CurrentUser User user, Long recycleId){
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        recycleService.deleteRecyclePost(user, recycleId);
    }

    @GetMapping("")
    @ApiResponse(responseCode = "200", description = "다시쓰기 목록 조회 성공")
    public GlobalResponse<PageImpl<RecycleResponse>> getRecycleList(@ParameterObject Pageable pageable){
        List<RecycleResponse> recycleList = recycleService.getRecycleList(pageable)
                .stream().map(Recycle::toResponse).toList();

        return new GlobalResponse<>(new PageImpl<>(recycleList, pageable, recycleList.size()));
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "다시쓰기 단건 조회 성공")
    public GlobalResponse<RecycleResponse> getRecycle(@PathVariable @NotNull(message = "게시물 id를 입력해주세요.") Long id){
        return new GlobalResponse<>(recycleService.getRecycle(id).toResponse());
    }

}
