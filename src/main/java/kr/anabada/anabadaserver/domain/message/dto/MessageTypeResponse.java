package kr.anabada.anabadaserver.domain.message.dto;

import lombok.Getter;

/**
 * 메세지를 응답으로 반환할때,<br/>
 * 메세지의 방향(누가 누구에게 보내주었는지)을 명시해주기 위해 사용하는 Enum <br/>
 */
@Getter
public enum MessageTypeResponse {
    NOTIFICATION, // 시스템이 보냄
    ME,
    INTERLOCUTOR
}
