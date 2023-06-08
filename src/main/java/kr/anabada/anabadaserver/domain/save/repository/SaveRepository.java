package kr.anabada.anabadaserver.domain.save.repository;

import kr.anabada.anabadaserver.domain.save.entity.Save;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaveRepository extends JpaRepository<Save, Long> {
    
}
