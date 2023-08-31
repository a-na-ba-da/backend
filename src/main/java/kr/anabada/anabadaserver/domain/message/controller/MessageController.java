package kr.anabada.anabadaserver.domain.message.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import kr.anabada.anabadaserver.common.dto.DomainType;
import kr.anabada.anabadaserver.domain.message.dto.MessageDetailResponse;
import kr.anabada.anabadaserver.domain.message.dto.MessageSendResponse;
import kr.anabada.anabadaserver.domain.message.dto.MessageSummaryResponse;
import kr.anabada.anabadaserver.domain.message.entity.Message;
import kr.anabada.anabadaserver.domain.message.service.MessageService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import kr.anabada.anabadaserver.global.response.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/message")
@Tag(name = "메세지(쪽지)", description = "메세지를 보내고 확인하는 API 모음")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("")
    @Operation(summary = "메세지 리스트", description = "내가 주고받은 메세지 방들의 요약 리스트를 가져옵니다.")
    public GlobalResponse<List<MessageSummaryResponse>> getMyAllMessageSummarized(@CurrentUser User user) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        return new GlobalResponse<>(
                messageService.getMyAllMessageSummarized(user)
        );
    }

    @GetMapping("/{messageRoomId}")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @Operation(summary = "메세지 상세 조회", description = "메세지방 ID로 메세지들을 가져옵니다. (마지막 element 의 timestamp 를 기점으로 한 커서 페지징)")
    public GlobalResponse<MessageDetailResponse> getMyMessageDetail(@CurrentUser User user,
                                                                    @Schema(description = "해당 timestamp 를 기점으로 이전 메세지를 페이징으로 불러옵니다. 만약 해당 파라메타를 넘기지 않는다면, 가장 최근 메세지를 불러옵니다. 형식 yyyy-MM-dd HH:mm:ss", nullable = true)
                                                                    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                                    @Nullable LocalDateTime timestamp,
                                                                    @Schema(description = "조회할 메세지방의 ID")
                                                                    @PathVariable long messageRoomId) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        return new GlobalResponse<>(
                messageService.getMyMessageDetail(user, timestamp, messageRoomId)
        );
    }

    @PostMapping("/{postType}/{postId}")
    @Operation(summary = "메세지 보내기", description = "게시물 타입과 ID를 통해 메세지를 보낼때 사용하는 API")
    public GlobalResponse<MessageSendResponse> sendMessage(@CurrentUser User user,
                                                           @Schema(description = "게시물 종류", allowableValues = {"BUY_TOGETHER", "BUY_TOGETHER", "RECYCLE"})
                                                           @PathVariable DomainType postType,
                                                           @Schema(description = "게시물 ID")
                                                           @PathVariable Long postId,
                                                           @Schema(description = "보낼 메세지", example = "안녕하세요~ 메세지 드려요^^")
                                                           @RequestBody String message) {
        if (user == null)
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);

        Message sentMessage = messageService.sendMessage(user, postType, postId, message);
        return new GlobalResponse<>(
                MessageSendResponse.builder()
                        .messageRoomId(sentMessage.getMessageOrigin().getId())
                        .messageId(sentMessage.getId())
                        .sentMessage(sentMessage.getContent())
                        .sentAt(sentMessage.getCreatedAt())
                        .build()
        );
    }

}
