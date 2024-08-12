package org.pplan.controller.share;

import lombok.AllArgsConstructor;
import org.pplan.service.dto.share.ShareDTO;
import org.pplan.service.share.ShareService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
public class ShareController {

    public final ShareService shareService;


    @PostMapping("/share/toggle")
    public void toggleLike(@RequestBody ShareDTO shareDTO) {

        shareService.toggleLike(shareDTO);
    }
    @GetMapping("/share/isLiked")
    public boolean isLiked(@RequestParam Long reviewId, @RequestParam Long userId) {
        System.out.println(userId);
        System.out.println(reviewId);
        ShareDTO shareDTO = new ShareDTO();
        shareDTO.setReviewId(reviewId);
        return shareService.checkLikeCount(shareDTO);
    }

}
