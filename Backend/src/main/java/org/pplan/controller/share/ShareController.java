package org.pplan.controller.share;

import lombok.AllArgsConstructor;
import org.pplan.service.dto.share.ShareDTO;
import org.pplan.service.share.ShareService;
import org.springframework.web.bind.annotation.*;

/**
 * 공유와 관련된 API를 제공하는 컨트롤러
 * @Author Kyeongmin
 */
@RestController
@AllArgsConstructor
@RequestMapping("/share")
public class ShareController {

    private final ShareService shareService;

    /**
     * 사용자가 특정 리뷰에 좋아요를 토글하는 API.
     *
     * @param shareDTO 사용자의 이메일과 리뷰 ID를 담고 있는 DTO
     */
    @PostMapping("/toggle")
    public void toggleLike(@RequestBody ShareDTO shareDTO) {

        shareService.toggleLike(shareDTO);
    }

    /**
     * 특정 리뷰에 대해 사용자가 좋아요를 눌렀는지 확인하는 API.
     *
     * @param reviewId  리뷰 ID
     * @param userEmail 사용자 이메일
     * @return 사용자가 좋아요를 눌렀는지 여부
     */
    @GetMapping("/isLiked")
    public boolean isLiked(@RequestParam Long reviewId, @RequestParam String userEmail) {
        ShareDTO shareDTO = new ShareDTO();
        shareDTO.setUserEmail(userEmail);
        shareDTO.setReviewId(reviewId);

        return shareService.checkLikeCount(shareDTO);

    }
}