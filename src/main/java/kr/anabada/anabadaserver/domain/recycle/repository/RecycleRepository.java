package kr.anabada.anabadaserver.domain.recycle.repository;

import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecycleRepository extends JpaRepository<Recycle, Long> {
    Optional<Recycle> findByIdAndWriter(Long recycleId, Long userId);

    List<Recycle> findByRecycleList(Pageable pageable);
}
