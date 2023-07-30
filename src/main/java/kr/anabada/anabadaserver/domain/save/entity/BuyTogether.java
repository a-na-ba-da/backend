package kr.anabada.anabadaserver.domain.save.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.common.entity.Image;
import kr.anabada.anabadaserver.domain.save.dto.response.BuyTogetherResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("BUY_TOGETHER")
@SQLDelete(sql = "UPDATE save SET is_removed = 1 WHERE id = ?")
public class BuyTogether extends Save {

    // 물건 전달 방법
    @Column(name = "is_parcel_delivery")
    private Boolean isParcelDelivery;

    // 구매 예정일 (NULL = 협의 or 상관없음)
    @Column(name = "buy_date")
    private LocalDate buyDate;

    // 너가 내야될 돈
    @Column(name = "pay", columnDefinition = "int unsigned not null")
    private Integer pay;

    // 오프라인 구매시 구매처 상세 정보
    @Column(name = "buy_place_detail")
    private String buyPlaceDetail;

    @BatchSize(size = 100)
    @JoinColumn(name = "post_id")
    @OneToMany(fetch = FetchType.LAZY)
    @Where(clause = "image_type = 'BUY_TOGETHER'")
    private List<Image> images;

    public BuyTogetherResponse toResponse() {
        return BuyTogetherResponse.builder()
                .id(getId())
                .title(getTitle())
                .content(getContent())
                .productUrl(getProductUrl())
                .createdAt(getCreatedAt())
                .modifiedAt(getModifiedAt())
                .isOnlineDeal(isOnlineDeal())
                .isParcelDelivery(isParcelDelivery)
                .deliveryPlaceLat(super.getPlaceLat())
                .deliveryPlaceLng(super.getPlaceLng())
                .buyDate(buyDate)
                .commentCount(getCommentCount())
                .images(images.stream().map(Image::getId).map(UUID::toString).toList())
                .writer(getWriter().toDto())
                .pay(pay)
                .build();
    }

    private boolean isOnlineDeal() {
        return !StringUtils.hasText(buyPlaceDetail);
    }
}
