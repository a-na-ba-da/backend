package kr.anabada.anabadaserver.domain.save.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "save")
@Where(clause = "is_removed = 0")
@DiscriminatorColumn(name = "save_type")
@SQLDelete(sql = "UPDATE save SET is_removed = 1 WHERE id = ?")
public class Save {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "writer", nullable = false)
    private Long writerId;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Column(name = "product_url", length = 500)
    private String productUrl;

    @Column(name = "buy_place_lat")
    private Double buyPlaceLat;

    @Column(name = "buy_place_lng")
    private Double buyPlaceLng;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "is_removed", nullable = false)
    private Boolean isRemoved = false;
}