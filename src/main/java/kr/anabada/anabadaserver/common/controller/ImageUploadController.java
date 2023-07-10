package kr.anabada.anabadaserver.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.anabada.anabadaserver.common.dto.DomainType;
import kr.anabada.anabadaserver.common.service.ImageUploadService;
import kr.anabada.anabadaserver.domain.user.entity.User;
import kr.anabada.anabadaserver.global.auth.CurrentUser;
import kr.anabada.anabadaserver.global.response.CustomException;
import kr.anabada.anabadaserver.global.response.ErrorCode;
import kr.anabada.anabadaserver.global.response.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
@Tag(name = "common", description = "각 도메인에서 공통으로 사용되는 API")
public class ImageUploadController {
    private final ImageUploadService imageUploadService;

    @PostMapping("")
    @Operation(
            summary = "이미지 업로드",
            description = "한개 혹은 여러개의 이미지를 업로드합니다. <br/>" +
                    "업로드할땐 이미지파일 바이너리를 Array 형태로 요청합니다. 이미지가 하나인 경우엔 Array 형태로 보낼 필요 없습니다. <br/> <br/>" +
                    "성공하면 이미지 파일 이름들을 Array 형태로 반환합니다."
    )
    @ApiResponse(
            responseCode = "200",
            description = "이미지 업로드 성공"
    )
    public GlobalResponse<List<String>> uploadImage(@CurrentUser User user,
                                                    @Parameter(description = "업로드할 이미지 파일들 (blob)", required = true)
                                                    MultipartFile[] uploadFile,
                                                    @RequestParam @Parameter(description = "이미지 타입", examples = {
                                                            @ExampleObject(name = "같이 사요", value = "BUY_TOGETHER", summary = "같이 사요 게시물에 사용되는 이미지"),
                                                            @ExampleObject(name = "같이 알아요", value = "KNOW_TOGETHER", summary = "같이 알아요 게시물에 사용되는 이미지"),
                                                            @ExampleObject(name = "물건(바꿔쓰기)", value = "MY_PRODUCT", summary = "바꿔쓰기에 사용되는 내 물건 이미지")
                                                    })
                                                    String type) {
        if (user == null) {
            throw new CustomException(ErrorCode.ONLY_ACCESS_USER);
        }

        if (uploadFile == null) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }

        DomainType imageType = DomainType.of(type);
        return new GlobalResponse<>(imageUploadService.uploadImages(uploadFile, imageType, user.getId()));
    }


    @GetMapping("/{fileName}")
    @Operation(
            summary = "이미지 다운로드",
            description = "이미지 파일 이름을 받아서 이미지를 다운로드합니다.<br/>" +
                    "이미지 파일의 바이너리를 반환합니다." +
                    "<br/><br/> 만약 썸네일용 이미지로 다운로드 하고 싶다면, 파일 이름의 맨앞(prefix)로 thumb 을 붙여서 요청하면 됩니다." +
                    "<br/> 예시 : /api/v1/image/thumbnail_이미지파일이름"
    )
    @ApiResponse(
            responseCode = "200",
            description = "이미지 다운로드 성공"
    )
    public byte[] downloadImage(
            @Parameter(description = "다운로드할 이미지 파일 이름<br/> 만약 썸네일 이미지로 조회하려면 prefix로 thumbnail_을 붙일 것", required = true)
            @PathVariable String fileName) {
        return imageUploadService.downloadImage(fileName);
    }
}
