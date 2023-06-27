package kr.anabada.anabadaserver.domain.save.service;

import kr.anabada.anabadaserver.common.service.ImageService;
import kr.anabada.anabadaserver.domain.save.dto.SaveSearchRequestDto;
import kr.anabada.anabadaserver.domain.save.dto.request.BuyTogetherRequest;
import kr.anabada.anabadaserver.domain.save.entity.BuyTogether;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import kr.anabada.anabadaserver.domain.save.repository.SaveRepository;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuyTogetherService {
    private final ImageService imageService;
    private final SaveRepository saveRepository;

    @Transactional
    public Save createNewBuyTogetherPost(User writer, BuyTogetherRequest request) {
        request.checkValidation();

        Save buyTogether = request.toEntity();
        buyTogether.setWriter(writer);

        // save
        Save save = saveRepository.save(buyTogether);
        // image Attach
        imageService.attach(writer.getId(), request.getImages(), save.getId());

        return save;
    }

    public List<BuyTogether> getBuyTogetherList(SaveSearchRequestDto searchRequest, Pageable pageable) {
        return saveRepository.findBuyTogetherList(searchRequest, pageable);
    }


    public BuyTogether getPost(Long id) {
        return saveRepository.findBuyTogetherById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
    }

    @Transactional
    public void removeMyPost(User user, Long buyTogetherId) {
        BuyTogether buyTogether = saveRepository.findBuyTogetherById(buyTogetherId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        if (!buyTogether.getWriter().getId().equals(user.getId()))
            throw new IllegalArgumentException("해당 게시물의 작성자가 아닙니다.");

        saveRepository.delete(buyTogether);
    }
}
