package kr.anabada.anabadaserver.domain.recycle.controller;

import kr.anabada.anabadaserver.domain.recycle.dto.RecycleDto;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.recycle.repository.RecycleRepository;
import kr.anabada.anabadaserver.domain.recycle.service.RecycleService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.exception.CustomException;
import kr.anabada.anabadaserver.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.source.spi.Sortable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recycle")
public class RecycleController {
    private final RecycleRepository recycleRepository;
    private final RecycleService recycleService;

    @PutMapping("")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void newPost(@CurrentUser User user,
                                  @RequestBody RecycleDto recycle){
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        recycleService.addNewPost(user.getId(), recycle);
    }

    @GetMapping("/like/{postId}")
    public void likePost(@CurrentUser User user, @PathVariable Long postId){
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        if (postId == null)
            throw new CustomException(ErrorCode.NOT_FOUND_RECYCLE_POST);

        recycleService.likePost(user.getId(), postId);
    }

    @GetMapping("/list")
    public List<RecycleDto> findList(Pageable pageable){
        // TODO springboot pagination 검색
        return null;
    }
}
