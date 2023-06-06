package kr.anabada.anabadaserver.domain.save.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("K")
public class KnowTogether extends Save {

    // 구매처 온오프라인 여부
    @Column(name = "is_online")
    private Boolean isOnline;

}
