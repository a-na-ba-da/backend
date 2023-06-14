package kr.anabada.anabadaserver.domain.save.controller;

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
public class KnowTogetherController {
    private final KnowTogetherService knowTogetherService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewKnowTogetherPost(@CurrentUser User user, @Validated KnowTogetherDto knowTogetherDto) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        knowTogetherDto.validate();
        knowTogetherService.createNewKnowTogetherPost(user, knowTogetherDto);
    }

    @GetMapping("")
    public Page<KnowTogetherDto> getKnowTogetherList(Pageable pageable, SaveSearchRequestDto searchRequest) {
        List<KnowTogetherDto> result = knowTogetherService.getKnowTogetherList(searchRequest, pageable);
        return new PageImpl<>(result, pageable, result.size());
    }

    @GetMapping("/{id}")
    public KnowTogetherDto getKnowTogether(@PathVariable @NotNull(message = "게시물 id를 입력해주세요.") Long id) {
        return knowTogetherService.getKnowTogether(id);
    }

}