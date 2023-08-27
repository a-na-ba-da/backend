package kr.anabada.anabadaserver.domain.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.anabada.anabadaserver.common.dto.DomainType;
import kr.anabada.anabadaserver.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Schema(description = "메세지 요약 응답")
public class MessageSummaryResponse {
    @Schema(description = "메세지방 ID")
    long messageRoomId;
    @Schema(description = "메세지가 시작된 게시물 타입")
    DomainType postType;
    @Schema(description = "메세지가 시작된 게시물 ID")
    Long postId;
    @Schema(description = "상대방 정보")
    UserDto interlocutor;
    @Schema(description = "마지막으로 주고받은 메세지 내용")
    String lastMessage;
    @Schema(description = "마지막으로 주고받은 메세지의 시간 yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime lastMessagedAt;
    @Schema(description = "내가 해당 채팅방에서 읽지 않은 메세지의 수 (0.. 1... ~  )")
    int unreadCount;

    public MessageSummaryResponse(long messageRoomId, DomainType postType, Long postId, UserDto interlocutor, String lastMessage, LocalDateTime lastMessagedAt, int unreadCount) {
        this.messageRoomId = messageRoomId;
        this.interlocutor = interlocutor;
        this.lastMessage = lastMessage;
        this.lastMessagedAt = lastMessagedAt;
        this.unreadCount = unreadCount;
        this.postType = postType;
        this.postId = postId;
    }
}
