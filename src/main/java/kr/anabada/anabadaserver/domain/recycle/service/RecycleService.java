package kr.anabada.anabadaserver.domain.recycle.service;

import kr.anabada.anabadaserver.domain.recycle.dto.request.RecyclePostRequestDto;
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

    @Transactional
    public Recycle createNewRecyclePost(User writer, RecyclePostRequestDto recyclePostRequestDto) {
        /*
            TODO : 게시물에 대한 추가적인 validation 필요 - 성훈 2023.07.22.
            예를 들면, 게시물의 제목이나 내용이 비어있는지, 혹은 글자수 제한이 있는지 등등

            1. Service Layer 에서 단독으로 처리 한다.
            2. Dto + Service Layer 에서 처리 한다. (권장)
         */

        Recycle newRecyclePost = recyclePostRequestDto.toEntity(writer, recyclePostRequestDto);

        return recycleRepository.save(newRecyclePost);
    }

    public void modifyRecyclePost(User user, Long recycleId, RecyclePostRequestDto recyclePostRequestDto) {
        Recycle originalPost = recycleRepository.findByIdAndWriter(recycleId, user.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        if(recyclePostRequestDto.getTitle() == null)
            throw new IllegalArgumentException("게시물 제목이 비어있습니다.");

        if(recyclePostRequestDto.getContent() == null)
            throw new IllegalArgumentException("게시물 내용이 비어있습니다.");

        if(!originalPost.getWriter().equals(user.getId()))
            throw new IllegalArgumentException("해당 게시물의 작성자가 아닙니다.");

        originalPost.setPost(recyclePostRequestDto.getTitle(), recyclePostRequestDto.getContent());
    }
}
