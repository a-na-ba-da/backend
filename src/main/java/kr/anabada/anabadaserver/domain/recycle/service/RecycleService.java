package kr.anabada.anabadaserver.domain.recycle.service;

import kr.anabada.anabadaserver.domain.recycle.dto.RecyclePostRequestDto;
import kr.anabada.anabadaserver.domain.recycle.dto.RecyclePostResponseDto;
import kr.anabada.anabadaserver.domain.recycle.entity.Recycle;
import kr.anabada.anabadaserver.domain.recycle.entity.RecycleLike;
import kr.anabada.anabadaserver.domain.recycle.repository.RecycleLikeRepository;
import kr.anabada.anabadaserver.domain.recycle.repository.RecycleRepository;
import kr.anabada.anabadaserver.global.exception.CustomException;
import kr.anabada.anabadaserver.global.exception.ErrorCode;
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
    private final RecycleLikeRepository recycleLikeRepository;

    @Transactional
    public Recycle addNewPost(long userId, RecyclePostRequestDto recycle) {
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
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RECYCLE));

        if (isDuplicateLike(userId, postId)){
            throw new CustomException(ErrorCode.CANT_DUPLICATE_LIKE);
        }

        RecycleLike recycleLike = new RecycleLike(post.getId(), userId);
        recycleLikeRepository.save(recycleLike);
    }

    public List<RecyclePostResponseDto> getRecycleList(Pageable pageable){
        return recycleRepository.findAll(pageable)
                .stream()
                .map(Recycle::toDto)
                .toList();
    }

    private boolean isDuplicateLike(Long userId, Long postId){
        return recycleLikeRepository.findByUserIdAndRecycleId(userId, postId).isPresent();
    }


    @Transactional
    public void editMyPost(Long userId, Long recycleId, RecyclePostRequestDto request) {
        Recycle originalPost = getPostIfMine(userId, recycleId);
        originalPost.editPost(request.getTitle(), request.getContent());
    }

    private Recycle getPostIfMine(Long userId, Long recycleId) {
        return recycleRepository.findByIdAndWriter(recycleId, userId)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Transactional
    public void deleteMyPost(Long userid, Long recycleId) {
        Recycle wantErasePost = recycleRepository.findByIdAndWriter(recycleId, userid)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RECYCLE));
    }
}
