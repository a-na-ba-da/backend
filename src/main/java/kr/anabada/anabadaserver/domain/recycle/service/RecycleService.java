package kr.anabada.anabadaserver.domain.recycle.service;

import kr.anabada.anabadaserver.common.service.ImageService;
import kr.anabada.anabadaserver.domain.recycle.dto.request.RecyclePostRequest;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.recycle.repository.RecycleRepository;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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

    public void modifyRecyclePost(User writer, Long recycleId, RecyclePostRequest recyclePostRequest) {
        recyclePostRequest.checkValidation();

        Recycle originalPost = recycleRepository.findByIdAndWriter(recycleId, writer.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        if(!originalPost.getWriter().equals(writer.getId()))
            throw new IllegalArgumentException("해당 게시물의 작성자가 아닙니다.");

        originalPost.setPost(recyclePostRequest);
    }

    public List<Recycle> getRecycleList(Pageable pageable) {
        return recycleRepository.findByRecycleList(pageable);
    }

    public Recycle getRecycle(Long id) {
        return recycleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
    }
}
