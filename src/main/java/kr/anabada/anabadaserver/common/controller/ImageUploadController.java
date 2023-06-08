package kr.anabada.anabadaserver.common.controller;

import kr.anabada.anabadaserver.common.dto.ImageType;
import kr.anabada.anabadaserver.common.service.ImageUploadService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.exception.CustomException;
import kr.anabada.anabadaserver.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageUploadController {
    private final ImageUploadService imageUploadService;

    @PostMapping("")
    public List<String> uploadImage(@CurrentUser User user, MultipartFile[] uploadFile, @RequestParam String type) {
        if (user == null) {
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);
        }

        if (uploadFile == null) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }

        ImageType imageType = ImageType.of(type);
        return imageUploadService.uploadImages(uploadFile, imageType, user.getId());
    }
}
