package kr.anabada.anabadaserver.domain.save.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import kr.anabada.anabadaserver.domain.save.dto.BuyTogetherDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("buy_together")
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

    public BuyTogetherDto toDto() {
        return BuyTogetherDto.builder()
                .id(getId())
                .title(getTitle())
                .content(getContent())
                .createdAt(getCreatedAt())
                .modifiedAt(getModifiedAt())
                .isOnlineDelivery(isOnlineDelivery)
                .buyDate(buyDate)
                .pay(pay)
                .build();
    }
}
