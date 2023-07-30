package kr.anabada.anabadaserver.domain.message.controller;

import kr.anabada.anabadaserver.common.dto.DomainType;
import kr.anabada.anabadaserver.domain.message.service.MessageService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/{postType}/{postId}")
    public void sendMessage(@CurrentUser User user, @PathVariable DomainType postType, @PathVariable Long postId) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        messageService.sendMessage(user, postType, postId);
    }

}
