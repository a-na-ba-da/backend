package kr.anabada.anabadaserver.domain.message.dto;

public enum MessageType {
    // 메세지 타입 ( 0 = 알림, 1 = sender가 보냄, 11 = receiver가 보냄)
    NOTIFICATION(0),
    SENDER_SEND(1),
    RECEIVER_SEND(11);

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

    public int getValue() {
        return value;
    }
}
