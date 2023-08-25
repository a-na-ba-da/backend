package kr.anabada.anabadaserver.domain.recycle.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "recycle_like")
@NoArgsConstructor
public class RecycleLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "recycle_id", nullable = false)
    private Long recycleId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Builder
    public RecycleLike(Long id, Long recycleId, Long userId) {
        this.id = id;
        this.recycleId = recycleId;
        this.userId = userId;
    }
}