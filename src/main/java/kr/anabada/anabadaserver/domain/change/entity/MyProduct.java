package kr.anabada.anabadaserver.domain.change.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "my_product")
public class MyProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "owner", nullable = false)
    private Long owner;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "price", columnDefinition = "int UNSIGNED not null")
    private Long price;

    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Column(name = "lat", nullable = false)
    private Double lat;

    @Column(name = "lng", nullable = false)
    private Double lng;

    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @Column(name = "category", length = 40)
    private String category;

    @Column(name = "is_removed", nullable = false)
    private Boolean isRemoved = false;

}