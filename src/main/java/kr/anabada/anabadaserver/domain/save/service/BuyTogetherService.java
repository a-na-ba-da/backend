package kr.anabada.anabadaserver.domain.save.service;

import kr.anabada.anabadaserver.common.service.ImageService;
import kr.anabada.anabadaserver.domain.save.dto.BuyTogetherDto;
import kr.anabada.anabadaserver.domain.save.dto.SaveSearchRequestDto;
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
    public Save createNewBuyTogetherPost(User writer, BuyTogetherDto buyTogetherDto) {
        buyTogetherDto.validate();
        
        Save buyTogether = buyTogetherDto.toEntity();
        buyTogether.setWriter(writer);

        // save
        Save save = saveRepository.save(buyTogether);
        // image Attach
        imageService.attach(writer.getId(), buyTogetherDto.getImages(), save.getId());

        return save;
    }

    public List<BuyTogetherDto> getBuyTogetherList(SaveSearchRequestDto searchRequest, Pageable pageable) {
        return saveRepository.findSaveList(searchRequest, pageable)
                .stream().map(BuyTogether::toDto).toList();
    }


    public BuyTogetherDto getBuyTogether(Long id) {
        return null;
    }
}
