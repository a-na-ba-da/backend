package kr.anabada.anabadaserver.domain.message.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.common.entity.BaseTimeEntity;
import lombok.Getter;

@Getter
@Entity
@Table(name = "message_origin")
public class MessageOrigin extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "message_type", nullable = false, length = 10)
    private String messageType;

    @Column(name = "message_detail_id", nullable = false)
    private Long messageDetailId;
}