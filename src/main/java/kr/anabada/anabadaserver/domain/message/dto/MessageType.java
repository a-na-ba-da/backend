package kr.anabada.anabadaserver.domain.message.dto;

import lombok.Getter;

/**
 * 메세지를 데이터베이스 상에 저장될 때,<br/>
 * 메세지의 방향(누가 누구에게 보내주었는지)을 명시해주기 위해 사용하는 Enum <br/>
 */
@Getter
public enum MessageType {
    NOTIFICATION(0), // 시스템이 보냄
    SENDER_SEND(1), // 전송자(Sender)가 보냄
    RECEIVER_SEND(11); // 수신자(Receiver)가 보냄

    private final int value;

    MessageType(int value) {
        this.value = value;
    }

    public static MessageType of(Integer dbData) {
        if (dbData == null) {
            return null;
        }

        for (MessageType messageType : MessageType.values()) {
            if (messageType.getValue() == dbData) {
                return messageType;
            }
        }

        return null;
    }
}
