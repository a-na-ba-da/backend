package kr.anabada.anabadaserver.domain.message.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.common.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "message")
public class Message extends BaseTimeEntity {
    @Column(name = "deleted_by_sender")
    private final Boolean deletedBySender = false;
    @Column(name = "deleted_by_receiver")
    private final Boolean deletedByReceiver = false;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "content", nullable = false, length = 500)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "message_origin_id", nullable = false)
    private MessageOrigin messageOrigin;

    @Builder
    public Message(String content, MessageOrigin messageOrigin) {
        this.content = content;
        this.messageOrigin = messageOrigin;
    }

    public static Message createWelcomeMessage(MessageOrigin messageOrigin, LocalDateTime createdAt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return Message.builder()
                .content(String.format("본 메세지는 %s님이 %s님에게 %s를 통해 메세지입니다. (%s에 자동 생성된 메세지)",
                        messageOrigin.getSender().getNickname(),
                        messageOrigin.getReceiver().getNickname(),
                        messageOrigin.getMessageType().getKo(),
                        formatter.format(createdAt))
                )
                .messageOrigin(messageOrigin)
                .build();
    }
}