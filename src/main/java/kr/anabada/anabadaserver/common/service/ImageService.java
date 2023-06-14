package kr.anabada.anabadaserver.common.service;

import kr.anabada.anabadaserver.common.entity.Image;
import kr.anabada.anabadaserver.common.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {
    private final ImageRepository imageRepository;

    @Transactional
    public void attach(long userId, List<String> imageNameList, long targetId) {
        List<UUID> uuidList = imageNameList.stream()
                .map(UUID::fromString)
                .toList();

        List<Image> images = imageRepository.findAllById(uuidList);
        for (Image image : images) {
            image.attach(targetId, userId);
        }

        imageRepository.saveAll(images);
    }
}
