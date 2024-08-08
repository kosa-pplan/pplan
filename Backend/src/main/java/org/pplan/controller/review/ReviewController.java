package org.pplan.controller.review;

import lombok.RequiredArgsConstructor;
import org.pplan.service.dto.review.ReviewRequestDTO;
import org.pplan.service.dto.review.ReviewResponseDTO;
import org.pplan.service.review.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/review/list")
    public List<ReviewResponseDTO> reviewList() {
        System.out.println("요청");
        return reviewService.reviewList();
    }

    @GetMapping("/review/{id}")
    public ReviewResponseDTO getReview(@PathVariable Long id) {
        return reviewService.getReview(id);
    }

    @PostMapping("/review/write")
    public void write(@RequestBody ReviewRequestDTO reviewRequestDTO) {
        reviewService.save(reviewRequestDTO);
    }

    @DeleteMapping("/review/{id}")
    public void delete(@PathVariable Long id) {
        reviewService.delete(reviewService.delete(id));
    }

}
