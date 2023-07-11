package kr.anabada.anabadaserver.domain.save.repository;

import kr.anabada.anabadaserver.domain.save.dto.request.SaveSearchRequestDto;
import kr.anabada.anabadaserver.domain.save.entity.BuyTogether;
import kr.anabada.anabadaserver.domain.save.entity.KnowTogether;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SaveRepositoryCustom {
    List<BuyTogether> findBuyTogetherList(SaveSearchRequestDto searchRequest, Pageable pageable);

    List<KnowTogether> findKnowTogetherList(SaveSearchRequestDto searchRequest, Pageable pageable);

    Long getPostWriterNotMine(Long myId, Long postId);
}
