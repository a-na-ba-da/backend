package kr.anabada.anabadaserver.domain.change.controller;

import jakarta.validation.Valid;
import kr.anabada.anabadaserver.domain.change.dto.MyProductRequest;
import kr.anabada.anabadaserver.domain.change.service.MyProductService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.exception.CustomException;
import kr.anabada.anabadaserver.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/changing/my-product")
public class MyProductController {
    private final MyProductService myProductService;

    @PostMapping("")
    public void createMyProduct(@CurrentUser User user, @Valid MyProductRequest request) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        myProductService.createMyProduct(user.getId(), request);
    }

}
