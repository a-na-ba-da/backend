package kr.anabada.anabadaserver.domain.change.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.common.entity.BaseTimeEntity;
import kr.anabada.anabadaserver.domain.change.dto.ChangeRequestStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "change_request")
public class ChangeRequest extends BaseTimeEntity {
    @OneToMany(mappedBy = "changeRequest")
    private final Set<ChangeRequestProduct> changedProducts = new HashSet<>();
    @Column(name = "removed_by_requestee", nullable = false)
    private final Boolean removedByRequestee = false;
    @Column(name = "removed_by_requester", nullable = false)
    private final Boolean removedByRequester = false;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Comment("상대물건 pk")
    @Column(name = "product_id", nullable = false)
    private Long productId;
    @Comment("요청 생성자 PK")
    @Column(name = "requester_id", nullable = false)
    private Long requesterId;
    @Comment("요청자의 메세지")
    @Column(name = "request_message", nullable = false)
    private String message;
    @Column(name = "reject_message", length = 30)
    private String rejectMessage;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 15)
    private ChangeRequestStatus status;

    @Builder
    public ChangeRequest(Long id, Long productId, Long requesterId, String message, String rejectMessage, ChangeRequestStatus status) {
        this.id = id;
        this.productId = productId;
        this.requesterId = requesterId;
        this.message = message;
        this.rejectMessage = rejectMessage;
        this.status = status;
    }
}