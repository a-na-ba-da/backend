package kr.anabada.anabadaserver.domain.save.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.common.entity.Image;
import kr.anabada.anabadaserver.domain.save.dto.response.KnowTogetherResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@DiscriminatorValue("KNOW_TOGETHER")
@SQLDelete(sql = "UPDATE save SET is_removed = 1 WHERE id = ?")
public class KnowTogether extends Save {

    // 구매처 온오프라인 여부
    @Column(name = "is_online")
    private Boolean isOnline;

    @Column(name = "buy_place_detail")
    private String buyPlaceDetail;

    @BatchSize(size = 100)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @Where(clause = "image_type = 'KNOW_TOGETHER'")
    private List<Image> images;

    public KnowTogetherResponse toDto() {
        return KnowTogetherResponse.builder()
                .id(super.getId())
                .title(super.getTitle())
                .content(super.getContent())
                .productUrl(super.getProductUrl())
                .createdAt(super.getCreatedAt())
                .modifiedAt(super.getModifiedAt())
                .buyPlaceDetail(buyPlaceDetail)
                .isOnline(isOnline)
                .images(images.stream().map(Image::getId).map(java.util.UUID::toString).toList())
                .writer(super.getWriter().toDto())
                .build();
    }
}
