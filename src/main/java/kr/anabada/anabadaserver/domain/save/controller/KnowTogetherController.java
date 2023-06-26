package kr.anabada.anabadaserver.domain.save.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.anabada.anabadaserver.domain.save.dto.SaveSearchRequestDto;
import kr.anabada.anabadaserver.domain.save.dto.request.KnowTogetherOfflineRequest;
import kr.anabada.anabadaserver.domain.save.dto.request.KnowTogetherOnlineRequest;
import kr.anabada.anabadaserver.domain.save.dto.response.KnowTogetherResponse;
import kr.anabada.anabadaserver.domain.save.service.KnowTogetherService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.exception.CustomException;
import kr.anabada.anabadaserver.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/saving/know-together")
@Tag(name = "아껴쓰기", description = "같이 알아요")
public class KnowTogetherController {
    private final KnowTogetherService knowTogetherService;

    @PostMapping("/online")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "생성 성공")
    @Operation(summary = "같이 알아요 생성 - case 1", description = "온라인 구매 꿀팁 글을 생성합니다.")
    public void createNewKnowTogetherOnlinePost(@CurrentUser User user, @RequestBody @Valid KnowTogetherOnlineRequest request) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        System.out.println(request.getImages());

        request.checkValidation();
        knowTogetherService.createNewKnowTogetherPost(user, request);
    }

    @PostMapping("/offline")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "생성 성공")
    @Operation(summary = "같이 알아요 생성 - case 2", description = "오프라인 구매 꿀팁 글을 생성합니다.")
    public void createNewKnowTogetherOfflinePost(@CurrentUser User user, @Valid KnowTogetherOfflineRequest request) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        request.checkValidation();
        knowTogetherService.createNewKnowTogetherPost(user, request);
    }

    @GetMapping("")
    @ApiResponse(responseCode = "200", description = "같이 알아요 목록 조회")
    public Page<KnowTogetherResponse> getKnowTogetherList(Pageable pageable, SaveSearchRequestDto searchRequest) {
        List<KnowTogetherResponse> result = knowTogetherService.getKnowTogetherList(searchRequest, pageable);
        return new PageImpl<>(result, pageable, result.size());
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "같이 알아요 단건 조회")
    public KnowTogetherResponse getKnowTogether(@PathVariable @NotNull(message = "게시물 id를 입력해주세요.") Long id) {
        return knowTogetherService.getKnowTogether(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponse(responseCode = "204", description = "본인이 작성한 같이 알아요 글 삭제 성공")
    public void deleteBuyTogether(@CurrentUser User user, @PathVariable @NotNull(message = "삭제할 게시물 id가 없습니다.") Long id) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        knowTogetherService.removeMyPost(user, id);
    }
}