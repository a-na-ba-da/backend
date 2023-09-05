package kr.anabada.anabadaserver.domain.share.service;

import kr.anabada.anabadaserver.common.service.ImageService;
import kr.anabada.anabadaserver.domain.share.dto.request.LendPostRequest;
import kr.anabada.anabadaserver.domain.share.entity.Lend;
import kr.anabada.anabadaserver.domain.share.repository.LendRepository;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LendService {
    private final LendRepository lendRepository;
    private final ImageService imageService;

    @Transactional
    public Lend createNewLendPost(User writer, LendPostRequest lendPostRequest) {
        lendPostRequest.checkValidation();

        Lend post = lendPostRequest.toEntity(writer, lendPostRequest);
        post.setWriter(writer);

        Lend newLendPost = lendRepository.save(post);
        imageService.attach(writer.getId(), lendPostRequest.getImages(), newLendPost.getId());

        return newLendPost;
    }

    @Transactional
    public void modifyLendPost(User writer, Long lendId, LendPostRequest lendPostRequest) {
        lendPostRequest.checkValidation();

        Lend originalPost = lendRepository.findByIdAndWriter(lendId, writer)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        if (!originalPost.getWriter().getId().equals(writer.getId()))
            throw new IllegalArgumentException("해당 게시물의 작성자가 아닙니다.");

        originalPost.editPost(lendPostRequest);
    }

    @Transactional
    public void deleteLendPost(User writer, Long lendId) {
        Lend post = lendRepository.findByIdAndWriter(lendId, writer)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        if (!post.getWriter().getId().equals(writer.getId()))
            throw new IllegalArgumentException("해당 게시물의 작성자가 아닙니다.");

        lendRepository.delete(post);
    }

    public List<Lend> getLendList(Pageable pageable) {
        return lendRepository.findByLendList(pageable);
    }

    public Lend getLend(Long lendId) {
        return lendRepository.findById(lendId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
    }
}
