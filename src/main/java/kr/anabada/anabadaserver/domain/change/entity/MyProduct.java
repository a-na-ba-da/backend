package kr.anabada.anabadaserver.domain.change.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.domain.change.dto.ProductStatus;
import kr.anabada.anabadaserver.domain.change.dto.ProductStatusConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static kr.anabada.anabadaserver.domain.change.dto.ProductStatus.AVAILABLE;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "my_product")
public class MyProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "owner", nullable = false)
    private Long owner;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "original_price", columnDefinition = "int UNSIGNED not null")
    private int originalPrice;

    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Builder.Default
    @Convert(converter = ProductStatusConverter.class)
    @Column(name = "status", nullable = false)
    private ProductStatus status = AVAILABLE;

//    @Column(name = "category", length = 40)
//    private String category;

    @Builder.Default
    @Column(name = "is_removed", nullable = false)
    private Boolean isRemoved = false;
}