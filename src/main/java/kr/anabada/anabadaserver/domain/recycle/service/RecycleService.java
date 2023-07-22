package kr.anabada.anabadaserver.domain.recycle.service;

import kr.anabada.anabadaserver.domain.recycle.dto.request.RecyclePostRequestDto;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.recycle.repository.RecycleRepository;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecycleService {
    private final RecycleRepository recycleRepository;

    @Transactional
    public Recycle createNewRecyclePost(User writer, RecyclePostRequestDto recyclePostRequestDto){
        Recycle newRecyclePost = recyclePostRequestDto.toEntity(writer, recyclePostRequestDto);

        return recycleRepository.save(newRecyclePost);
    }

}
