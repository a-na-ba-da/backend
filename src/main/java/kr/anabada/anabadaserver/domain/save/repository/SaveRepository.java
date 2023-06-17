package kr.anabada.anabadaserver.domain.save.repository;

import kr.anabada.anabadaserver.domain.save.entity.BuyTogether;
import kr.anabada.anabadaserver.domain.save.entity.KnowTogether;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaveRepository extends JpaRepository<Save, Long>, SaveRepositoryCustom {

    Optional<BuyTogether> findBuyTogetherById(Long id);

    Optional<KnowTogether> findKnowTogetherById(Long id);
}
