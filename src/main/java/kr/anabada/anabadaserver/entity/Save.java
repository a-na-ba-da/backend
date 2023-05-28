package kr.anabada.anabadaserver.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "save")
public class Save {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "writer", nullable = false)
    private Long writer;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Column(name = "is_online_delivery")
    private Boolean isOnlineDelivery;

    @Column(name = "buy_date")
    private LocalDate buyDate;

    @Column(name = "product_url", length = 500)
    private String productUrl;

    @Column(name = "pay")
    private Integer pay;

    @Column(name = "is_online")
    private Boolean isOnline;

    @Column(name = "buy_place_lat")
    private Double buyPlaceLat;

    @Column(name = "buy_place_lng")
    private Double buyPlaceLng;

    @Column(name = "save_type", nullable = false)
    private Boolean saveType = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "is_removed", nullable = false)
    private Boolean isRemoved = false;

}