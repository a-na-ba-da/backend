package kr.anabada.anabadaserver.domain.save.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.common.entity.Image;
import kr.anabada.anabadaserver.domain.save.dto.BuyTogetherDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("BUY_TOGETHER")
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

    @BatchSize(size = 100)
    @JoinColumn(name = "post_id")
    @OneToMany(fetch = FetchType.LAZY)
    @Where(clause = "image_type = 'BUY_TOGETHER'")
    private List<Image> images;

    public BuyTogetherDto toDto() {
        return BuyTogetherDto.builder()
                .id(getId())
                .title(getTitle())
                .content(getContent())
                .createdAt(getCreatedAt())
                .modifiedAt(getModifiedAt())
                .isOnlineDelivery(isOnlineDelivery)
                .buyDate(buyDate)
                .images(images.stream().map(Image::getId).map(UUID::toString).toList())
                .userDto(getWriter().toDto())
                .pay(pay)
                .build();
    }
}
