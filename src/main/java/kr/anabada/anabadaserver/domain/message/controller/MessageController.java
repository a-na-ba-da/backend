package kr.anabada.anabadaserver.domain.message.controller;

import kr.anabada.anabadaserver.common.dto.DomainType;
import kr.anabada.anabadaserver.domain.message.dto.MessageSummaryResponse;
import kr.anabada.anabadaserver.domain.message.service.MessageService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import kr.anabada.anabadaserver.global.response.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("")
    public GlobalResponse<List<MessageSummaryResponse>> getMessageList(@CurrentUser User user) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        return new GlobalResponse<>(
                messageService.getMyAllMessageSummarized(user)
        );
    }

    @GetMapping("/{postType}/{postId}")
    public void sendMessage(@CurrentUser User user, @PathVariable DomainType postType, @PathVariable Long postId,
                            @RequestBody String message) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        messageService.sendMessage(user, postType, postId, message);
    }

}
