package kr.anabada.anabadaserver.domain.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.anabada.anabadaserver.common.dto.DomainType;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Schema(description = "메세지 상세 조회 응답")
public class MessageDetailResponse {
    @Schema(description = "조회해온 메세지 내용")
    final List<MessageDetail> messages = new ArrayList<>();
    @Schema(description = "메세지가 시작되게된 게시물 타입")
    DomainType messagePostType;
    @Schema(description = "메세지가 시작된 게시물의 ID")
    Long messagePostId;
    @Schema(description = "메시지방 ID")
    Long messageRoomId;
    @Schema(description = "상대방 정보")
    UserDto interlocutor;

    public MessageDetailResponse(DomainType messagePostType, Long messagePostId, Long messageRoomId, UserDto interlocutor) {
        this.messagePostType = messagePostType;
        this.messagePostId = messagePostId;
        this.messageRoomId = messageRoomId;
        this.interlocutor = interlocutor;
    }

    public void addMessageResponse(long id, String message, LocalDateTime sentAt, MessageTypeResponse sentWho) {
        messages.add(new MessageDetail(id, message, sentAt, sentWho));
    }

    @Getter
    @Schema(description = "조회해온 메세지 하나하나의 정보")
    static class MessageDetail {
        @Schema(description = "메세지 ID")
        long id;
        @Schema(description = "메세지 내용")
        String message;
        @Schema(description = "메세지의 방향을 나타내는 Enum (조회자가 보냄, 조회자가 받음, 시스템이 보낸 메세지)")
        MessageTypeResponse sentWho;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "메세지가 보내진 시간")
        LocalDateTime sentAt;

        public MessageDetail(long id, String message, LocalDateTime sentAt, MessageTypeResponse sentWho) {
            this.id = id;
            this.message = message;
            this.sentWho = sentWho;
            this.sentAt = sentAt;
        }
    }
}
