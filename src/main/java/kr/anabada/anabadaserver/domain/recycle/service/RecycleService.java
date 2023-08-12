package kr.anabada.anabadaserver.domain.recycle.service;

import kr.anabada.anabadaserver.common.service.ImageService;
import kr.anabada.anabadaserver.domain.recycle.dto.request.RecyclePostRequest;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.recycle.repository.RecycleRepository;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecycleService {
    private final RecycleRepository recycleRepository;
    private final ImageService imageService;

    @Transactional
    public Recycle createNewRecyclePost(User writer, RecyclePostRequest recyclePostRequest) {
        recyclePostRequest.checkValidation();

        Recycle newRecyclePost = recyclePostRequest.toEntity(writer, recyclePostRequest);

        imageService.attach(writer.getId(), recyclePostRequest.getImages(), newRecyclePost.getId());

        return recycleRepository.save(newRecyclePost);
    }
}
