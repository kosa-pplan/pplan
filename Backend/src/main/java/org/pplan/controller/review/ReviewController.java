package org.pplan.controller.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.pplan.service.dto.myPage.MyPageDTO;
import org.pplan.service.dto.review.ReviewDTO;
import org.pplan.service.dto.review.ReviewImageDTO;
import org.pplan.service.dto.review.ReviewListDTO;
import org.pplan.service.review.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Log4j2
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 목록을 가져오는 API
     * @return 리뷰 DTO 리스트
     */
    @GetMapping("/list")
    public ResponseEntity<List<ReviewListDTO>> reviewList() {
        List<ReviewListDTO> reviews = reviewService.reviewList();
        return ResponseEntity.ok(reviews);
    }

    /**
     * 특정 리뷰를 가져오는 API
     * @param id 리뷰 ID
     * @return 리뷰 DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long id) {
        ReviewDTO review = reviewService.getReview(id);
        return review != null ? ResponseEntity.ok(review) : ResponseEntity.notFound().build();
    }

    /**
     * 새로운 리뷰를 작성하고 이미지를 업로드하는 API
     * @param title 리뷰 제목
     * @param contents 리뷰 내용
     * @param images 리뷰에 첨부할 이미지 배열
     * @return 저장된 리뷰 DTO
     */
    @PostMapping("/write")
    public ResponseEntity<ReviewDTO> write(
            @RequestParam("courseId") Long courseId,
            @RequestParam("title") String title,
                                           @RequestParam("contents") String contents,
                                           @RequestParam("images") MultipartFile[] images) {
        try {
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setTitle(title);
            reviewDTO.setContents(contents);
            reviewDTO.setCourseId(courseId);

            // 이미지를 처리하여 DTO에 설정
            List<ReviewImageDTO> reviewImageDTOList = reviewService.processImages(images);
            reviewDTO.setReviewImageDTOList(reviewImageDTOList);

            ReviewDTO savedReviewDTO = reviewService.saveReview(reviewDTO);
            return ResponseEntity.ok(savedReviewDTO);
        } catch (IOException e) {
            log.error("Error processing images", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    /**
     * 리뷰를 삭제하는 API
     * @param id 리뷰 ID
     * @return HTTP 상태 코드
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            reviewService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            log.error("Error deleting review with id: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 사용자가 좋아요한 리뷰 목록을 가져오는 API
     * @param userEmail 사용자 이메일
     * @return 좋아요한 리뷰 목록
     */
    @GetMapping("/myPage/myLikeReview")
    public ResponseEntity<List<MyPageDTO>> myPageDTOList(@RequestParam String userEmail) {
        List<MyPageDTO> likedReviews = reviewService.getMyLikeReview(userEmail);
        return ResponseEntity.ok(likedReviews);
    }
}