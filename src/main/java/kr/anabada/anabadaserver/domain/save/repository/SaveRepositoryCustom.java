package kr.anabada.anabadaserver.domain.save.repository;

import kr.anabada.anabadaserver.domain.save.dto.SaveSearchRequestDto;
import kr.anabada.anabadaserver.domain.save.entity.BuyTogether;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SaveRepositoryCustom {
    List<BuyTogether> findSaveList(SaveSearchRequestDto searchRequest, Pageable pageable);
}