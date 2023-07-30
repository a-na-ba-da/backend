package kr.anabada.anabadaserver.domain.message.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.common.dto.DomainType;
import kr.anabada.anabadaserver.common.entity.BaseTimeEntity;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "message_origin")
public class MessageOrigin extends BaseTimeEntity {
    @OneToMany(mappedBy = "messageOrigin")
    private final List<Message> messages = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "message_type", nullable = false, length = 10)
    private DomainType messageType;
    @Column(name = "message_post_id", nullable = false)
    private Long messagePostId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Builder
    public MessageOrigin(DomainType messageType, User sender, User receiver, Long messagePostId) {
        this.messageType = messageType;
        this.sender = sender;
        this.receiver = receiver;
        this.messagePostId = messagePostId;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }
}