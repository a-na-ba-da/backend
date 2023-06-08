package kr.anabada.anabadaserver.domain.save.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("know_together")
public class KnowTogether extends Save {

    // 구매처 온오프라인 여부
    @Column(name = "is_online")
    private Boolean isOnline;
}
