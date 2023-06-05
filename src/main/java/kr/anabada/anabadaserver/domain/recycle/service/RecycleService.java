package kr.anabada.anabadaserver.domain.recycle.service;

import kr.anabada.anabadaserver.domain.recycle.dto.RecycleDto;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.recycle.entity.RecycleLike;
import kr.anabada.anabadaserver.domain.recycle.repository.RecycleLikeRepository;
import kr.anabada.anabadaserver.domain.recycle.repository.RecycleRepository;
import kr.anabada.anabadaserver.global.exception.CustomException;
import kr.anabada.anabadaserver.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecycleService {
    private final RecycleRepository recycleRepository;
    private final RecycleLikeRepository recycleLikeRepository;

    @Transactional
    public Recycle addNewPost(long userId, RecycleDto recycle) {
        Recycle newPost = Recycle.builder()
                .title(recycle.getTitle())
                .content(recycle.getContent())
                .writer(userId)
                .build();

        return recycleRepository.save(newPost);
    }

    @Transactional
    public void likePost(Long userId, Long postId) {
        Recycle post = recycleRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RECYCLE_POST));

        recycleLikeRepository.findByUserIdAndRecycleId(userId, postId)
                .orElseThrow(() -> new CustomException(ErrorCode.CANT_DUPLICATE_LIKE));

        RecycleLike recycleLike = new RecycleLike(post.getId(), userId);
        recycleLikeRepository.save(recycleLike);
    }
}
