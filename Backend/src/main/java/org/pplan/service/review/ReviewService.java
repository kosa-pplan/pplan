package org.pplan.service.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.pplan.repository.mapper.ReviewMapper;
import org.pplan.service.dto.myPage.MyPageDTO;
import org.pplan.service.dto.review.ReviewDTO;
import org.pplan.service.dto.review.ReviewImageDTO;
import org.pplan.service.dto.review.ReviewListDTO;
import org.pplan.service.image.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final ImageService imageService;

    @Transactional
    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        // 리뷰 저장
        reviewMapper.insertReview(reviewDTO);

        Long reviewId = reviewDTO.getId();

        // 이미지 저장
        saveReviewImages(reviewDTO.getReviewImageDTOList(), reviewId);

        reviewMapper.updateYesCourseStatus(reviewDTO.getCourseId());
        return reviewDTO;
    }

    private void saveReviewImages(List<ReviewImageDTO> imageDTOList, Long reviewId) {
        for (ReviewImageDTO imageDTO : imageDTOList) {
            reviewMapper.insertReviewImage(
                    imageDTO.getUrlPath(),
                    imageDTO.getSUrlPath(),
                    reviewId
            );
        }
    }

    public List<ReviewListDTO> reviewList() {
        return reviewMapper.reviewList();
    }

    public ReviewDTO getReview(long id) {
        return reviewMapper.getReview(id);
    }

    @Transactional
    public void update(ReviewDTO reviewDTO) {
        long rowsAffected = reviewMapper.reviewUpdate(reviewDTO);
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("Review not found or update failed.");
        }
    }

    @Transactional
    public void delete(long id) {
        log.info("Deleting review with id: {}", id);
        long rowsAffected = reviewMapper.reviewDelete(id);
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("Review not found or delete failed.");
        }
    }

    public List<MyPageDTO> getMyLikeReview(String userEmail) {
        return reviewMapper.getMyLikeReview(userEmail);
    }

    public List<ReviewImageDTO> processImages(MultipartFile[] images) throws IOException {
        List<ReviewImageDTO> reviewImageDTOList = new ArrayList<>();
        for (MultipartFile image : images) {
            String[] processedImageData = imageService.processImage(image);
            ReviewImageDTO reviewImageDTO = new ReviewImageDTO();
            reviewImageDTO.setUrlPath(processedImageData[0]);
            reviewImageDTO.setSUrlPath(processedImageData[1]);
            reviewImageDTOList.add(reviewImageDTO);
        }
        return reviewImageDTOList;
    }
}