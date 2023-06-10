package kr.anabada.anabadaserver.domain.save.repository;

import kr.anabada.anabadaserver.domain.save.entity.BuyTogether;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaveRepository extends JpaRepository<Save, Long> {

    List<BuyTogether> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
