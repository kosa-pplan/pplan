package org.pplan.service.image;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @Author Kyeongmin
 * 이미지 처리 서비스단
 */
@Service
public class ImageService {

    @Value("${image.upload-dir}")
    private String uploadDir;

    /**
     * 이미지 파일을 처리하고 저장하며, 원본 이미지와 썸네일의 파일명을 반환합니다.
     *
     * @param image 업로드된 이미지 파일
     * @return 원본 이미지와 썸네일 이미지의 파일명 배열
     * @throws IOException 파일 처리 중 오류가 발생할 경우
     */
    public String[] processImage(MultipartFile image) throws IOException {
        // 원본 파일 이름 정리 및 확장자 추출
        String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));

        // UUID를 이용한 새로운 파일 이름 생성
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + fileExtension;

        // 파일 저장 경로 설정
        Path filePath = Paths.get(uploadDir, fileName);
        Path thumbnailPath = Paths.get(uploadDir, "thumbnail_" + fileName);

        // 디렉토리 생성 (존재하지 않을 경우)
        Files.createDirectories(filePath.getParent());

        // 원본 파일 저장
        image.transferTo(filePath.toFile());

        // 썸네일 생성
        Thumbnails.of(filePath.toFile())
                .size(100, 100)
                .toFile(thumbnailPath.toFile());

        return new String[]{fileName, "thumbnail_" + fileName};
    }
}