package org.pplan.controller.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.pplan.service.dto.myPage.MyPageDTO;
import org.pplan.service.dto.review.ReviewDTO;
import org.pplan.service.dto.review.ReviewImageDTO;
import org.pplan.service.dto.review.ReviewListDTO;
import org.pplan.service.dto.review.ReviewWriterCheckDTO;
import org.pplan.service.review.ReviewService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 리뷰와 관련된 API를 제공하는 컨트롤러
 *
 * @Author Kyeongmin
 */
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Log4j2
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 모든 리뷰의 목록을 가져오는 API.
     *
     * @return 리뷰 목록을 담은 ResponseEntity 객체
     */
    @GetMapping("/list")
    public ResponseEntity<List<ReviewListDTO>> getReviewList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        // 리뷰 리스트와 총 리뷰 수 가져오기
        List<ReviewListDTO> reviews = reviewService.reviewList(page, size);
        long totalReviews = reviewService.getTotalReviewCount();

        // 헤더에 총 리뷰 수 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(totalReviews));

        // 리뷰 리스트와 헤더를 포함한 응답 반환
        return ResponseEntity.ok().headers(headers).body(reviews);
    }

    /**
     * 특정 리뷰를 가져오는 API.
     *
     * @param id 리뷰 ID
     * @return 요청된 리뷰를 담은 ResponseEntity 객체. 리뷰가 존재하지 않으면 404 상태 코드 반환
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable Long id) {
        ReviewDTO review = reviewService.getReview(id);
        return review != null ? ResponseEntity.ok(review) : ResponseEntity.notFound().build();
    }

    /**
     * 새로운 리뷰를 작성하고 이미지 파일을 업로드하는 API.
     *
     * @param courseId 과정 ID
     * @param title    리뷰 제목
     * @param contents 리뷰 내용
     * @param images   첨부할 이미지 파일 배열
     * @return 저장된 리뷰를 담은 ResponseEntity 객체. 이미지 처리 오류 시 500 상태 코드 반환
     */
    @PostMapping("/write")
    public ResponseEntity<ReviewDTO> writeReview(
            @RequestParam("courseId") Long courseId,
            @RequestParam("title") String title,
            @RequestParam("contents") String contents,
            @RequestParam("images") MultipartFile[] images) {
        try {
            if (images == null) {
                System.out.println("No images were provided.");
            } else {
                System.out.println("Number of images received: " + images.length);
            }

            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setTitle(title);
            reviewDTO.setContents(contents);
            reviewDTO.setCourseId(courseId);

            List<ReviewImageDTO> reviewImageDTOList = reviewService.processImages(images);
            reviewDTO.setReviewImageDTOList(reviewImageDTOList);

            ReviewDTO savedReviewDTO = reviewService.saveReview(reviewDTO);
            return ResponseEntity.ok(savedReviewDTO);
        } catch (IOException e) {
            log.error("Error processing images", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 특정 리뷰를 삭제하는 API.
     *
     * @param id 삭제할 리뷰의 ID
     * @return 204 No Content 상태 코드. 리뷰가 존재하지 않을 경우 404 상태 코드 반환
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        System.out.println("ID: "+id);
        try {
            reviewService.deleteReview(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 사용자가 좋아요를 누른 리뷰 목록을 가져오는 API.
     *
     * @param userEmail 사용자 이메일
     * @return 좋아요를 누른 리뷰 목록을 담은 ResponseEntity 객체
     */
    @GetMapping("/myPage/myLikeReview")
    public ResponseEntity<List<MyPageDTO>> getLikedReviews(@RequestParam String userEmail) {
        List<MyPageDTO> likedReviews = reviewService.getMyLikeReview(userEmail);
        return ResponseEntity.ok(likedReviews);
    }

    /**
     * 주어진 courseId로 작성자의 이메일을 및 코스에 대한 리뷰가 저장되었는지 조회하는 API.
     *
     * @param courseId 과정 ID
     * @return reviewWriterCheckDTO
     */
    @GetMapping("/creator")
    public ResponseEntity<ReviewWriterCheckDTO> getAuthorCheckYNInfoByCourseId(@RequestParam Long courseId) {
        ReviewWriterCheckDTO reviewWriterCheckDTO = reviewService.getAuthorCheckYNInfoByCourseId(courseId);

        return ResponseEntity.ok(reviewWriterCheckDTO);
    }

    /**
     * 리뷰가 존재 여부를 확인하는 API.
     *
     * @param reviewId 리뷰 ID
     * @return 리뷰 ID의 존재 여부를 나타내는 수치
     */
    @GetMapping("/checkReviewCount")
    public ResponseEntity<Integer> checkReviewCount(@RequestParam Long reviewId) {
        int count = reviewService.checkCountReviewId(reviewId);
        return ResponseEntity.ok(count);
    }
}