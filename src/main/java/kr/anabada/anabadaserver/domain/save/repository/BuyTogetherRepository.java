package kr.anabada.anabadaserver.domain.save.repository;

import kr.anabada.anabadaserver.domain.save.entity.BuyTogether;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyTogetherRepository extends JpaRepository<BuyTogether, Long> {
    Page<BuyTogether> findAllByOrderByIdDesc(Pageable pageable);
}
