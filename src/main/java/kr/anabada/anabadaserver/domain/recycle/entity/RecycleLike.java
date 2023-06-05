package kr.anabada.anabadaserver.domain.recycle.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "recycle_like")
public class RecycleLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "recycle_id", nullable = false)
    private Long recycleId;

    @Column(name = "user_id", nullable = false)
    private Long userId;


    public RecycleLike(Long postId, Long userId){
        this.recycleId = postId;
        this.userId = userId;
    }

}