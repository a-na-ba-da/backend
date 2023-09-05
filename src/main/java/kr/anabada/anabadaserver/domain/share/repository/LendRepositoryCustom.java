package kr.anabada.anabadaserver.domain.share.repository;

import kr.anabada.anabadaserver.domain.share.entity.Lend;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LendRepositoryCustom {
    
    List<Lend> findByLendList(Pageable pageable);
}
