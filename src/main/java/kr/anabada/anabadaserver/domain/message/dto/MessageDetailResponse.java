package kr.anabada.anabadaserver.domain.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.anabada.anabadaserver.common.dto.DomainType;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class MessageDetailResponse {
    final List<MessageDetail> messages = new ArrayList<>();
    DomainType messagePostType;
    Long messageRoomId;
    UserDto interlocutor;

    public MessageDetailResponse(DomainType messagePostType, Long messageRoomId, UserDto interlocutor) {
        this.messagePostType = messagePostType;
        this.messageRoomId = messageRoomId;
        this.interlocutor = interlocutor;
    }

    public void addMessageResponse(long id, String message, LocalDateTime sentAt, MessageTypeResponse sentWho) {
        messages.add(new MessageDetail(id, message, sentAt, sentWho));
    }

    @Getter
    static class MessageDetail {
        long id;
        String message;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        MessageTypeResponse sentWho;
        LocalDateTime sentAt;

        public MessageDetail(long id, String message, LocalDateTime sentAt, MessageTypeResponse sentWho) {
            this.id = id;
            this.message = message;
            this.sentWho = sentWho;
            this.sentAt = sentAt;
        }
    }
}
