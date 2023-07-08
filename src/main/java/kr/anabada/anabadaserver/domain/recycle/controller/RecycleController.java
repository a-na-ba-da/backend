package kr.anabada.anabadaserver.domain.recycle.controller;

import jakarta.validation.Valid;
import kr.anabada.anabadaserver.domain.recycle.dto.RecyclePostRequestDto;
import kr.anabada.anabadaserver.domain.recycle.dto.RecyclePostResponseDto;
import kr.anabada.anabadaserver.domain.recycle.repository.RecycleRepository;
import kr.anabada.anabadaserver.domain.recycle.service.RecycleService;
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
@RequestMapping("/api/v1/recycle")
public class RecycleController {
    private final RecycleRepository recycleRepository;
    private final RecycleService recycleService;

    
    // 게시글 생성
    @PostMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void newPost(@CurrentUser User user, @RequestBody @Valid RecyclePostRequestDto recycle){
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        recycleService.addNewPost(user.getId(), recycle);
    }

    // 게시글 수정
    @PatchMapping("/{recycleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeMyPost(@CurrentUser User user, Long recycleId, RecyclePostRequestDto request){
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        recycleService.editMyPost(user.getId(), recycleId, request);
    }

    // 게시글 좋아요 
    @GetMapping("/like/{postId}")
    public void likePost(@CurrentUser User user, @PathVariable Long postId){
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        if (postId == null)
            throw new CustomException(ErrorCode.NOT_FOUND_RECYCLE);

        recycleService.likePost(user.getId(), postId);
    }

    // 게시글 목록 조회
    @GetMapping("")
    public Page<RecyclePostResponseDto> getRecycleList(Pageable pageable){
        List<RecyclePostResponseDto> result = recycleService.getRecycleList(pageable);
        return new PageImpl<>(result, pageable, result.size());
    }


    // 게시글 삭제
    @DeleteMapping("/{recycleId}")
    public void deleteMyPost(@CurrentUser User user, Long recycleId){
        if(user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        recycleService.deleteMyPost(user.getId(), recycleId);
    }


}
