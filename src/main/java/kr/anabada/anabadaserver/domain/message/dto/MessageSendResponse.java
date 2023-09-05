package kr.anabada.anabadaserver.domain.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MessageSendResponse {
    @Schema(description = "쪽지방 id")
    private long messageRoomId;
    @Schema(description = "내가 방금 보낸 메세지의 id")
    private long messageId;
    @Schema(description = "내가 방금 보낸 메세지 내용")
    private String sentMessage;
    @Schema(description = "내가 방금 보낸 메세지의 실제 전송시각")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sentAt;

    @Builder
    public MessageSendResponse(long messageRoomId, long messageId, String sentMessage, LocalDateTime sentAt) {
        this.messageRoomId = messageRoomId;
        this.messageId = messageId;
        this.sentMessage = sentMessage;
        this.sentAt = sentAt;
    }
}
