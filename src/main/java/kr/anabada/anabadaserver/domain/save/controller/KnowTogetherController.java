package kr.anabada.anabadaserver.domain.save.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import kr.anabada.anabadaserver.domain.save.dto.KnowTogetherDto;
import kr.anabada.anabadaserver.domain.save.dto.SaveSearchRequestDto;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/saving/know-together")
@Tag(name = "아껴쓰기", description = "같이 알아요")
public class KnowTogetherController {
    private final KnowTogetherService knowTogetherService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "같이 알아요 생성 성공")
    public void createNewKnowTogetherPost(@CurrentUser User user, @Validated KnowTogetherDto knowTogetherDto) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        knowTogetherDto.validate();
        knowTogetherService.createNewKnowTogetherPost(user, knowTogetherDto);
    }

    @GetMapping("")
    @ApiResponse(responseCode = "200", description = "같이 알아요 목록 조회")
    public Page<KnowTogetherDto> getKnowTogetherList(Pageable pageable, SaveSearchRequestDto searchRequest) {
        List<KnowTogetherDto> result = knowTogetherService.getKnowTogetherList(searchRequest, pageable);
        return new PageImpl<>(result, pageable, result.size());
    }

    @GetMapping("/{id}")
    @ApiResponse(responseCode = "200", description = "같이 알아요 단건 조회")
    public KnowTogetherDto getKnowTogether(@PathVariable @NotNull(message = "게시물 id를 입력해주세요.") Long id) {
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