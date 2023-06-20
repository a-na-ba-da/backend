package kr.anabada.anabadaserver.domain.save.entity;

import jakarta.persistence.*;
import kr.anabada.anabadaserver.common.entity.BaseTimeEntity;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Where;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "save")
@Where(clause = "is_removed = 0")
@DiscriminatorColumn(name = "save_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Save extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "writer", nullable = false)
    private User writer;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, length = 700)
    private String content;

    @Column(name = "product_url", length = 500)
    private String productUrl;

    @Column(name = "buy_place_lat")
    private Double buyPlaceLat;

    @Column(name = "buy_place_lng")
    private Double buyPlaceLng;

    @Builder.Default
    @Column(name = "is_removed", nullable = false)
    private Boolean isRemoved = false;

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setRemoved(Boolean removed) {
        isRemoved = removed;
    }
}