package kr.anabada.anabadaserver.domain.recycle.repository;

import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecycleRepository extends JpaRepository<Recycle, Long>, RecycleRepositoryCustom {
    Optional<Recycle> findByIdAndWriter(Long recycleId, User userId);
}
