package org.pplan.controller.review;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.pplan.service.dto.review.ReviewDTO;
import org.pplan.service.dto.review.ReviewImageDTO;
import org.pplan.service.review.ReviewService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
 * CrossOrigin 역할:
 * Spring Framework에서 제공하는 어노테이션으로,
 * 특정 컨트롤러나 메소드에 대해 CORS 요청을 허용하는 역할을 합니다.
 * 리소스를 요청하는 보안 메커니즘을 제어할 수 있습니다.
 */
@CrossOrigin
@RestController
@RequiredArgsConstructor
public class ReviewController {

    // ReviewService 의존성 주입
    private final ReviewService reviewService;

    // 이미지 업로드 디렉토리 경로를 application.properties에서 가져오기
    @Value("${image.upload-dir}")
    private String uploadDir;
    private static final String PATH_SEPARATOR = getSeparator();

    // OS에 따라 경로 구분자를 반환하는 메소드
    private static String getSeparator() {
        return System.getProperty("os.name").contains("mac") ? File.separator : "/";
    }

    /**
     * 이미지 파일을 다운로드하는 API
     * @param filename 파일명
     * @return 이미지 파일의 Resource
     */
    @GetMapping("/imgs/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path file = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok().body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 리뷰 목록을 가져오는 API
     * @return 리뷰 DTO 리스트
     */
    @GetMapping("/review/list")
    public List<ReviewDTO> reviewList() {
        return reviewService.reviewList();
    }

    /**
     * 특정 리뷰를 가져오는 API
     * @param id 리뷰 ID
     * @return 리뷰 DTO
     */
    @GetMapping("/review/{id}")
    public ReviewDTO getReview(@PathVariable Long id) {
        return reviewService.getReview(id);
    }

    /**
     * 새로운 리뷰를 작성하고 이미지를 업로드하는 API
     * @param title 리뷰 제목
     * @param contents 리뷰 내용
     * @param images 리뷰에 첨부할 이미지 배열
     * @return 저장된 리뷰 DTO
     * @throws IOException 파일 처리 중 발생할 수 있는 예외
     */
    @PostMapping("/review/write")
    public ResponseEntity<ReviewDTO> write(@RequestParam("title") String title,
                                           @RequestParam("contents") String contents,
                                           @RequestParam("images") MultipartFile[] images) throws IOException {

        // 리뷰 DTO 객체 생성
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setTitle(title);
        reviewDTO.setContents(contents);

        // 리뷰 이미지 DTO 리스트 생성
        List<ReviewImageDTO> reviewImageDTOList = new ArrayList<>();
        for (MultipartFile image : images) {
            // 원본 파일 이름 및 확장자 추출
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

        // 리뷰와 이미지 정보를 저장
        reviewDTO.setReviewImageDTOList(reviewImageDTOList);

        ReviewDTO savedReviewDTO = reviewService.saveReview(reviewDTO);
        return ResponseEntity.ok(savedReviewDTO);
    }

    /**
     * 리뷰를 삭제하는 API
     * @param id 리뷰 ID
     */
    @DeleteMapping("/review/{id}")
    public void delete(@PathVariable Long id) {
        reviewService.delete(reviewService.delete(id));
    }
}