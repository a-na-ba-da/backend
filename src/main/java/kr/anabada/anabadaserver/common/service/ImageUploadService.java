package kr.anabada.anabadaserver.common.service;

import kr.anabada.anabadaserver.common.dto.ImageType;
import kr.anabada.anabadaserver.common.entity.Image;
import kr.anabada.anabadaserver.common.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageUploadService {
    private final ImageRepository imageRepository;

    @Transactional
    public List<String> uploadImages(MultipartFile[] uploadFile, ImageType imageType, long uploaderId) {
        List<String> imageNameList = new ArrayList<>();
        for (MultipartFile file : uploadFile) {
            // validate file
            OriginalFileInfo originalFileInfo = validateFile(file);

            // check image directory
            File directory = getImageDirectory();

            // save image and generate thumbnail
            UUID uuid = UUID.randomUUID();
            File savedFile = saveImageOnLocal(directory, uuid, file);
            makeThumbnail(directory, uuid.toString(), savedFile);

            // save image info on database
            imageRepository.save(
                    Image.builder()
                            .id(uuid)
                            .originalFileName(originalFileInfo.fileName())
                            .extension(originalFileInfo.extension())
                            .uploader(uploaderId)
                            .imageType(imageType.getTypeStr())
                            .build()
            );

            imageNameList.add(uuid.toString());
        }

        return imageNameList;
    }

    private File getImageDirectory() {
        File directory = null;
        try {
            directory = new File(ResourceUtils.getFile("classpath:").getPath(), "images");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("이미지 디렉터리 접근 실패");
        }
        // 애플리케이션의 작업 디렉토리에 images 디렉토리가 없으면 생성
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IllegalStateException("디렉토리 생성 실패");
        }

        return directory;
    }

    private File saveImageOnLocal(File directory, UUID uuid, MultipartFile imageFile) {
        File saveFile = new File(directory, uuid.toString());
        try {
            imageFile.transferTo(saveFile);
        } catch (Exception e) {
            throw new RuntimeException("이미지 저장 실패");
        }

        return saveFile;
    }

    private OriginalFileInfo validateFile(MultipartFile file) {
        String fullFileName = file.getOriginalFilename();
        if (fullFileName == null || fullFileName.isBlank()) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다.");
        }
        String fileName = fullFileName.substring(0, fullFileName.lastIndexOf("."));
        String extension = fullFileName.substring(fullFileName.lastIndexOf(".") + 1);

        // allow only image file with file format
        if (!extension.equalsIgnoreCase("jpg") &&
                !extension.equalsIgnoreCase("jpeg") &&
                !extension.equalsIgnoreCase("png")) {
            throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다. ");
        }

        return OriginalFileInfo.of(fileName, extension);
    }


    private void makeThumbnail(File directory, String uuidStr, File saveFile) {
        // generate thumbnail image
        BufferedImage bo_img = null;
        try {
            bo_img = ImageIO.read(saveFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int maxWidth = 750;
        int calcHeight = (int) (bo_img.getHeight() * ((double) maxWidth / bo_img.getWidth()));

        File thumbnailFile = new File(directory, "thumbnail_" + uuidStr);
        try {
            Thumbnails.of(saveFile)
                    .size(maxWidth, calcHeight)
                    .toFile(thumbnailFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void deleteImage(String imageName) {
        // remove image info on database
        imageRepository.deleteById(UUID.fromString(imageName));
        // remove image file
        File directory = getImageDirectory();
        File imageFile = new File(directory, imageName);
        File thumbnailFile = new File(directory, "thumbnail_" + imageName);
        if (!imageFile.delete() || !thumbnailFile.delete()) {
            throw new RuntimeException("이미지 삭제 실패");
        }


    }

    private record OriginalFileInfo(String fileName, String extension) {
        public static OriginalFileInfo of(String fileName, String extension) {
            return new OriginalFileInfo(fileName, extension);
        }
    }


}