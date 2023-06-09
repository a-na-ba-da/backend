package kr.anabada.anabadaserver.domain.recycle;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "recycle_like")
public class RecycleLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "recycle_id", nullable = false)
    private Long recycleId;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}