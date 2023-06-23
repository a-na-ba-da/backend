package kr.anabada.anabadaserver.domain.change.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "change_request")
public class ChangeRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "requester_id", nullable = false)
    private Long requesterId;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "requester_product", nullable = false)
    private Long requesterProduct;

    @Column(name = "reject_message", length = 30)
    private String rejectMessage;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "is_removed", nullable = false)
    private Boolean isRemoved = false;
}