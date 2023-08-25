package kr.anabada.anabadaserver.domain.change.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.common.entity.BaseTimeEntity;
import kr.anabada.anabadaserver.domain.change.dto.ChangeRequestStatus;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "change_request")
public class ChangeRequest extends BaseTimeEntity {
    @Column(name = "removed_by_requestee", nullable = false)
    private final Boolean removedByRequestee = false;
    @Column(name = "removed_by_requester", nullable = false)
    private final Boolean removedByRequester = false;
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "changeRequest", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ChangeRequestProduct> toChangeProducts = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @Comment("상대물건 PK")
    @JoinColumn(name = "product_id")
    private MyProduct targetProduct;
    @Comment("요청 생성자 PK")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private User requester;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requestee_id")
    private User requestee;
    @Comment("요청자의 메세지")
    @Column(name = "request_message", nullable = false)
    private String message;
    @Column(name = "reject_message", length = 30)
    private String rejectMessage;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 15)
    private ChangeRequestStatus status;

    @Builder
    public ChangeRequest(MyProduct targetProduct, User requester, User requestee, String message, String rejectMessage, ChangeRequestStatus status) {
        this.targetProduct = targetProduct;
        this.requester = requester;
        this.requestee = requestee;
        this.message = message;
        this.rejectMessage = rejectMessage;
        this.status = status;
    }

    public void setStatus(ChangeRequestStatus changeRequestStatus) {
        if (changeRequestStatus != null)
            this.status = changeRequestStatus;
    }
}