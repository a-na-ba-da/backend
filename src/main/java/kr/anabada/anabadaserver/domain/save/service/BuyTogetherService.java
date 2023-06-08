package kr.anabada.anabadaserver.domain.save.service;

import kr.anabada.anabadaserver.domain.save.dto.BuyTogetherDto;
import kr.anabada.anabadaserver.domain.save.entity.BuyTogether;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import kr.anabada.anabadaserver.domain.save.repository.BuyTogetherRepository;
import kr.anabada.anabadaserver.domain.save.repository.SaveRepository;
import kr.anabada.anabadaserver.global.exception.CustomException;
import kr.anabada.anabadaserver.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BuyTogetherService {
    private final BuyTogetherRepository buyTogetherRepository;
    private final SaveRepository saveRepository;

    @Transactional
    public Save createNewBuyTogetherPost(long userId, BuyTogetherDto buyTogetherDto) {
        Save buyTogether = buyTogetherDto.toEntity();
        buyTogether.setWriterId(userId);
        return saveRepository.save(buyTogether);
    }

    public Page<BuyTogetherDto> getBuyTogetherList(Pageable pageable) {
        Page<BuyTogether> searchResult = buyTogetherRepository.findAllByOrderByIdDesc(pageable);
        return pageToDto(searchResult);
    }


    private Page<BuyTogetherDto> pageToDto(Page<BuyTogether> pageResult) {
        List<BuyTogetherDto> result = pageResult.map(BuyTogether::toDto).toList();

        return new PageImpl<>(result, pageResult.getPageable(), pageResult.getTotalElements());
    }

    public BuyTogetherDto getBuyTogether(Long id) {
        return buyTogetherRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_BUY_TOGETHER)).toDto();
    }
}
