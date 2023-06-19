package kr.anabada.anabadaserver.domain.recycle.repository;

import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecycleRepository extends JpaRepository<Recycle, Long> {
    List<Recycle> findRecycleList(Pageable pageable);
}
