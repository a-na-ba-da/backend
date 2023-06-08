package kr.anabada.anabadaserver.common.service;

import kr.anabada.anabadaserver.common.dto.ImageType;
import kr.anabada.anabadaserver.common.repository.ImageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

import static org.springframework.util.ResourceUtils.getFile;

@ExtendWith(MockitoExtension.class)
class ImageUploadServiceTest {
    @Spy
    @InjectMocks
    ImageUploadService imageUploadService;

    @Mock
    ImageRepository imageRepository;

 /*   @Test
    @DisplayName("이미지 업로드 성공 - jpg, png, jpeg")
    void 이미지_업로드_성공() throws IOException {
        // Given - 여러개 이미지 업로드
        MultipartFile multipartFile1 = new MockMultipartFile("test.jpg", "test.jpg", "image/jpg", new ClassPathResource("test.jpg").getInputStream());
        MultipartFile multipartFile2 = new MockMultipartFile("test.jpeg", "test.jpeg", "image/jpeg", new ClassPathResource("test.jpeg").getInputStream());
        MultipartFile multipartFile3 = new MockMultipartFile("test.png", "test.png", "image/png", new ClassPathResource("test.png").getInputStream());

        MultipartFile[] multipartFiles = {multipartFile1, multipartFile2, multipartFile3};
        ImageType imageType = ImageType.BUY_TOGETHER;
        long uploaderId = 1L;

        // When - Mocking
        when(imageRepository.save(any(Image.class))).thenAnswer(invocation -> {
            Image image = invocation.getArgument(0);
            ReflectionTestUtils.setField(image, "id", UUID.randomUUID());
            return image;
        });

        // When
        List<String> imageNameList = imageUploadService.uploadImages(multipartFiles, imageType, uploaderId);

        // Then
        Assertions.assertEquals(3, imageNameList.size());

        // 이미지 삭제
//        for (String imageName : imageNameList) {
//            imageUploadService.deleteImage(imageName);
//        }
    }*/

    @Test
    @DisplayName("이미지 업로드 실패 - 지원하지 않는 확장자")
    void 이미지_업로드_실패_지원하지_않는_확장자() {
        // Given - gif file
        MultipartFile multipartFile = new MockMultipartFile("test.gif", "test.gif", "image/gif", "test".getBytes());
        ImageType imageType = ImageType.BUY_TOGETHER;
        long uploaderId = 1L;

        // When & Then - throw exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> imageUploadService.uploadImages(new MultipartFile[]{multipartFile}, imageType, uploaderId));
    }

    @Test
    @DisplayName("이미지 업로드 실패 - 파일이름이 없음")
    void 이미지_업로드_실패_파일_이름이_없음() {
        // Given (No original file name)
        MultipartFile multipartFile = new MockMultipartFile("test.png", "", "image/png", "test".getBytes());
        ImageType imageType = ImageType.BUY_TOGETHER;
        long uploaderId = 1L;

        // When & Then - throw exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> imageUploadService.uploadImages(new MultipartFile[]{multipartFile}, imageType, uploaderId));
    }

    @Test
    @DisplayName("이미지 업로드 실패 - 이미지 디렉터리 접근 실패")
    void 이미지_업로드_실패_저장폴더_접근_실패() throws FileNotFoundException {
        // Given
        MultipartFile multipartFile = new MockMultipartFile("test.png", "test.png", "image/png", "test".getBytes());
        ImageType imageType = ImageType.BUY_TOGETHER;
        long uploaderId = 1L;

        // When - Mock static method
        MockedStatic<ResourceUtils> mockResourceUtils = Mockito.mockStatic(ResourceUtils.class);
        mockResourceUtils.when(() -> getFile("classpath:")).thenThrow(new FileNotFoundException());

        // Then - throw exception
        Assertions.assertThrows(RuntimeException.class, () -> imageUploadService.uploadImages(new MultipartFile[]{multipartFile}, imageType, uploaderId));
    }

    @Test
    @DisplayName("이미지 업로드 실패 - 이미지 타입 누락")
    void 이미지_업로드_실패_이미지_타입_누락() {
        // Given
        ImageType imageType = null;
        MultipartFile multipartFile = new MockMultipartFile("test.gif", "test.gif", "image/gif", "test".getBytes());
        long uploaderId = 1L;

        // When & Then - throw exception
        Assertions.assertThrows(IllegalArgumentException.class, () -> imageUploadService.uploadImages(new MultipartFile[]{multipartFile}, imageType, uploaderId));
    }
}