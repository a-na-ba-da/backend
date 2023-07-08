package kr.anabada.anabadaserver.domain.recycle.repository;

import kr.anabada.anabadaserver.domain.recycle.entity.RecycleLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecycleLikeRepository extends JpaRepository<RecycleLike, Long> {
    Optional<RecycleLike> findByUserIdAndRecycleId(long userId, long recycleId);
}
