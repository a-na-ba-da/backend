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
    final List<MessageDetail> messageDetail = new ArrayList<>();
    DomainType messageOriginType;
    Long messageOriginId;
    UserDto interlocutor;

    public MessageDetailResponse(DomainType messageOriginType, Long messageOriginId, UserDto interlocutor) {
        this.messageOriginType = messageOriginType;
        this.messageOriginId = messageOriginId;
        this.interlocutor = interlocutor;
    }

    @Getter
    static class MessageDetail {
        String message;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime sentAt;
        MessageType type;

        public MessageDetail(String message, LocalDateTime sentAt, MessageType type) {
            this.message = message;
            this.sentAt = sentAt;
            this.type = type;
        }
    }
}
