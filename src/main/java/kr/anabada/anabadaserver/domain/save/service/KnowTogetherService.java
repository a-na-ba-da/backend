package kr.anabada.anabadaserver.domain.save.service;

import kr.anabada.anabadaserver.common.service.ImageService;
import kr.anabada.anabadaserver.domain.save.dto.KnowTogetherDto;
import kr.anabada.anabadaserver.domain.save.entity.Save;
import kr.anabada.anabadaserver.domain.save.repository.SaveRepository;
import kr.anabada.anabadaserver.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KnowTogetherService {
    private final ImageService imageService;
    private final SaveRepository saveRepository;

    @Transactional
    public Save createNewKnowTogetherPost(User writer, KnowTogetherDto knowTogetherDto) {
        knowTogetherDto.validate();

        Save knowTogether = knowTogetherDto.toEntity();
        knowTogether.setWriter(writer);

        // save
        Save save = saveRepository.save(knowTogether);
        // image Attach
        imageService.attach(writer.getId(), knowTogetherDto.getImages(), save.getId());

        return save;
    }
}
