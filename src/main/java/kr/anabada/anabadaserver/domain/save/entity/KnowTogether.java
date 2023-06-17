package kr.anabada.anabadaserver.domain.save.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.common.entity.Image;
import kr.anabada.anabadaserver.domain.save.dto.KnowTogetherDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("KNOW_TOGETHER")
@SQLDelete(sql = "UPDATE save SET is_removed = 1 WHERE id = ?")
public class KnowTogether extends Save {

    // 구매처 온오프라인 여부
    @Column(name = "is_online")
    private Boolean isOnline;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @Where(clause = "image_type = 'KNOW_TOGETHER'")
    private List<Image> images;

    public KnowTogetherDto toDto() {
        return KnowTogetherDto.builder()
                .id(getId())
                .title(getTitle())
                .content(getContent())
                .productUrl(getProductUrl())
                .createdAt(getCreatedAt())
                .modifiedAt(getModifiedAt())
                .isOnlineBought(isOnline)
                .images(images.stream().map(Image::getId).map(java.util.UUID::toString).toList())
                .userDto(getWriter().toDto())
                .build();
    }
}
