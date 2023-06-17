package kr.anabada.anabadaserver.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.anabada.anabadaserver.common.dto.DomainType;
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

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
@Tag(name = "common", description = "각 도메인에서 공통으로 사용되는 API")
public class ImageUploadController {
    private final ImageUploadService imageUploadService;

    @PostMapping("")
    @Operation(summary = "이미지 업로드", description = "한개 혹은 여러개의 이미지를 업로드합니다. 성공하면 이미지 파일 이름들을 Array 형태로 반환합니다.")
    @ApiResponse(responseCode = "200", description = "이미지 업로드 성공")
    public List<String> uploadImage(@CurrentUser User user,
                                    @Schema(description = "업로드할 이미지 파일들", requiredMode = REQUIRED)
                                    MultipartFile[] uploadFile,
                                    @RequestParam @Schema(description = "이미지 타입", example = "BUY_TOGETHER, KNOW_TOGETHER ....")
                                    String type) {
        if (user == null) {
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);
        }

        if (uploadFile == null) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }

        DomainType imageType = DomainType.of(type);
        return imageUploadService.uploadImages(uploadFile, imageType, user.getId());
    }
}
