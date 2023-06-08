package kr.anabada.anabadaserver.domain.save.controller;

import jakarta.validation.constraints.NotNull;
import kr.anabada.anabadaserver.domain.save.dto.BuyTogetherDto;
import kr.anabada.anabadaserver.domain.save.repository.BuyTogetherRepository;
import kr.anabada.anabadaserver.domain.save.service.BuyTogetherService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.exception.CustomException;
import kr.anabada.anabadaserver.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/saving")
@RequiredArgsConstructor
public class SavingController {
    private final BuyTogetherService buyTogetherService;
    private final BuyTogetherRepository buyTogetherRepository;

    @PostMapping("/buy-together")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewBuyTogetherPost(@CurrentUser User user, @Validated BuyTogetherDto buyTogetherDto) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        buyTogetherService.createNewBuyTogetherPost(user.getId(), buyTogetherDto);
    }

    @GetMapping("/buy-together")
    public Page<BuyTogetherDto> getBuyTogetherList(Pageable pageable) {
        return buyTogetherService.getBuyTogetherList(pageable);
    }

    @GetMapping("/buy-together/{id}")
    public BuyTogetherDto getBuyTogether(@PathVariable @NotNull(message = "게시물 id를 입력해주세요.") Long id) {
        return buyTogetherService.getBuyTogether(id);
    }

    @PutMapping("/save/{id}")
    public String modifySaving(@PathVariable Long id) {
        return "save";
    }
}
