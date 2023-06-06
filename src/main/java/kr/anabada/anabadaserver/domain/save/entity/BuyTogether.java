package kr.anabada.anabadaserver.domain.save.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("B")
public class BuyTogether extends Save {

    // 물건 전달 방법
    @Column(name = "is_online_delivery")
    private Boolean isOnlineDelivery;

    // 구매 예정일
    @Column(name = "buy_date")
    private LocalDate buyDate;

    // 너가 내야될 돈
    @Column(name = "pay")
    private Integer pay;
}
