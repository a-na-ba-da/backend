package kr.anabada.anabadaserver.domain.recycle.repository;

import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecycleRepositoryCustom {
    List<Recycle> findByRecycleList(Pageable pageable);
}
