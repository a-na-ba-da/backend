package kr.anabada.anabadaserver.domain.change.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.common.entity.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "change_request_product")
public class ChangeRequestProduct extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

//    @Column(name = "change_request_id", nullable = false)
//    private Long changeRequestId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "change_request_id")
    private ChangeRequest changeRequest;


    @Builder
    public ChangeRequestProduct(Long id, Long productId) {
        this.id = id;
        this.productId = productId;
    }

    public void setChangeRequest(ChangeRequest changeRequest) {
        changeRequest.getChangedProducts().add(this);
        this.changeRequest = changeRequest;
    }
}