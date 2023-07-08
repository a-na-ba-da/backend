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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private MyProduct product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "change_request_id")
    private ChangeRequest changeRequest;

    @Builder
    public ChangeRequestProduct(Long id, MyProduct product) {
        this.id = id;
        this.product = product;
    }

    public void setChangeRequest(ChangeRequest changeRequest) {
        changeRequest.getToChangeProducts().add(this);
        this.changeRequest = changeRequest;
    }
}