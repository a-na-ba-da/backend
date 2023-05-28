package kr.anabada.anabadaserver.domain.share.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "lend")
public class Lend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "writer", nullable = false)
    private Long writer;

    @Column(name = "title", length = 50)
    private String title;

    @Column(name = "start", nullable = false)
    private LocalDateTime start;

    @Column(name = "end")
    private LocalDateTime end;

    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Column(name = "price_per_day", columnDefinition = "int UNSIGNED not null")
    private Long pricePerDay;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng", nullable = false)
    private Double lng;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "is_removed", nullable = false)
    private Boolean isRemoved = false;

    @Column(name = "status", nullable = false)
    private Boolean status = false;

}