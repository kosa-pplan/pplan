package org.pplan.service.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.pplan.repository.mapper.ReviewMapper;
import org.pplan.service.dto.review.ReviewDTO;
import org.pplan.service.dto.review.ReviewImageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewService {

    public final ReviewMapper reviewMapper;

    @Value("${image.upload-dir}")
    private String uploadDir;

    @Transactional
    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        // 리뷰 저장
        reviewMapper.insertReview(reviewDTO);

        Long reviewId = reviewDTO.getId();

        // 이미지 저장
        List<ReviewImageDTO> imageDTOList = reviewDTO.getReviewImageDTOList();
        for (ReviewImageDTO imageDTO : imageDTOList) {
            reviewMapper.insertReviewImage(
                    imageDTO.getUrlPath(),
                    imageDTO.getSUrlPath(),
                    reviewId
            );
        }
        // 저장된 리뷰와 이미지를 포함하여 DTO를 반환
        return reviewDTO;
    }

    public List<ReviewDTO> reviewList() {
        List<ReviewDTO> reviews = reviewMapper.reviewList();

        return reviews;
    }

    public ReviewDTO getReview(long id) {
        return reviewMapper.getReview(id);
    }

    public long update(ReviewDTO reviewRequestDTO) {

        return reviewMapper.reviewUpdate(reviewRequestDTO);
    }

    public long delete(long id) {
        log.info("delete.............");
        return reviewMapper.reviewDelete(id);
    }
}
