package org.pplan.controller.review;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.pplan.service.dto.review.ReviewDTO;
import org.pplan.service.dto.review.ReviewImageDTO;
import org.pplan.service.review.ReviewService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * CrossOrigin 역할
 * SpringFrameWork에서 제공하는 어노테이션,
 * 특정 컨틀롤러나 메소드에 대해 CORS요청을 허용하는 역할
 * 리소스를 요청하는 보안 메커니즘, @CrossOrigin 어노테이션을 사용하면 제어 가능
 */

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ReviewController {

    public final ReviewService reviewService;

    @Value("${image.upload-dir}")
    private String uploadDir;
    private static final String PATH_SEPARATOR = getSeparator();

    private static String getSeparator() {
        return System.getProperty("os.name").contains("mac") ? File.separator : "/";
    }
    @GetMapping("/review/images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        Path filePath = Paths.get(uploadDir).resolve(filename);
        Resource file = new FileSystemResource(filePath);

        System.out.println("이미지 요청");
        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG) // 실제 이미지 MIME 타입에 맞게 설정
                .body(file);
    }

    @GetMapping("/review/list")
    public List<ReviewDTO> reviewList() {
        return reviewService.reviewList();
    }

    @GetMapping("/review/{id}")
    public ReviewDTO getReview(@PathVariable Long id) {
        return reviewService.getReview(id);
    }

    @PostMapping("/review/write")
    public ResponseEntity<ReviewDTO> write(@RequestParam("title") String title,
                                           @RequestParam("contents") String contents,
                                           @RequestParam("images") MultipartFile[] images) throws IOException {

        // 1.객체 생성
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setTitle(title);
        reviewDTO.setContents(contents);

        List<ReviewImageDTO> reviewImageDTOList = new ArrayList<>();
        for (MultipartFile image : images) {
            // 원본 파일 이름 생성
            String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf('.'));
            String uuid = UUID.randomUUID().toString();
            String fileName = uuid + fileExtension;
            File dest = new File(uploadDir + File.separator + fileName);
            image.transferTo(dest);

            // 썸네일 생성
            File thumbnailFile = new File(uploadDir + File.separator + "thumbnail_" + fileName);
            Thumbnails.of(dest).size(100, 100).toFile(thumbnailFile);

            // 이미지 메타데이터 생성
            ReviewImageDTO reviewImageDTO = new ReviewImageDTO();
            reviewImageDTO.setUrlPath(fileName);
            reviewImageDTO.setSUrlPath("thumbnail_" + fileName);
            reviewImageDTOList.add(reviewImageDTO);
        }
        System.out.println(uploadDir +"uploadDir");

        // 3. 리뷰와 이미지를 저장
        reviewDTO.setReviewImageDTOList(reviewImageDTOList);

        ReviewDTO savedReviewDTO = reviewService.saveReview(reviewDTO);
        return ResponseEntity.ok(savedReviewDTO);
    }

    @DeleteMapping("/review/{id}")
    public void delete(@PathVariable Long id) {
        reviewService.delete(reviewService.delete(id));
    }

}
