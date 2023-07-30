package kr.anabada.anabadaserver.domain.message.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.common.entity.BaseTimeEntity;
import lombok.Getter;

@Getter
@Entity
@Table(name = "message")
public class Message extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Column(name = "deleted_by_sender", nullable = false)
    private Boolean deletedBySender = false;

    @Column(name = "deleted_by_receiver", nullable = false)
    private Boolean deletedByReceiver = false;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Column(name = "message_type", nullable = false, length = 10)
    private String messageType;

    @Column(name = "message_detail_id", nullable = false)
    private Long messageDetailId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "message_origin_id", nullable = false)
    private MessageOrigin messageOrigin;
}