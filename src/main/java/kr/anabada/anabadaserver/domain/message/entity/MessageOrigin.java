package kr.anabada.anabadaserver.domain.message.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "message_origin")
public class MessageOrigin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "message_type", nullable = false, length = 10)
    private String messageType;

    @Column(name = "message_detail_id", nullable = false)
    private Long messageDetailId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}