package kr.anabada.anabadaserver.domain.change.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.common.entity.Image;
import kr.anabada.anabadaserver.domain.change.dto.ProductStatus;
import kr.anabada.anabadaserver.domain.change.dto.ProductStatusConverter;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

import static kr.anabada.anabadaserver.domain.change.dto.ProductStatus.AVAILABLE;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "my_product")
@Where(clause = "is_removed = 0")
@SQLDelete(sql = "UPDATE my_product SET is_removed = 1 WHERE id = ?")
public class MyProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "owner", nullable = false)
    private User owner;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "original_price", columnDefinition = "int UNSIGNED not null")
    private int originalPrice;

    @Column(name = "content", nullable = false, length = 700)
    private String content;

    @Builder.Default
    @Convert(converter = ProductStatusConverter.class)
    @Column(name = "status", nullable = false)
    private ProductStatus status = AVAILABLE;

//    @Column(name = "category", length = 40)
//    private String category;

    @BatchSize(size = 100)
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @Where(clause = "image_type = 'MY_PRODUCT'")
    private List<Image> images;

    @Builder.Default
    @Column(name = "is_removed", nullable = false)
    private Boolean isRemoved = false;

//  쓸 일이 없어 일단 주석 처리 하였음.
//    public MyProductResponse toResponse() {
//        return MyProductResponse.builder()
//                .id(id)
//                .name(name)
//                .user(owner == null ? null : owner.toDto())
//                .originalPrice(originalPrice)
//                .content(content)
//                .status(status)
//                .images(images == null ? null : images.stream().map(Image::getId).map(UUID::toString).toList())
//                .build();
//    }
}