package kr.anabada.anabadaserver.domain.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MessageSummaryResponse {
    long messageRoomId;
    UserDto interlocutor;
    String lastMessage;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime lastMessagedAt;
    int unreadCount;

    public MessageSummaryResponse(long messageRoomId, UserDto interlocutor, String lastMessage, LocalDateTime lastMessagedAt, int unreadCount) {
        this.messageRoomId = messageRoomId;
        this.interlocutor = interlocutor;
        this.lastMessage = lastMessage;
        this.lastMessagedAt = lastMessagedAt;
        this.unreadCount = unreadCount;
    }
}
