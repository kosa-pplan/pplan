package org.pplan.service.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.pplan.repository.mapper.ReviewMapper;
import org.pplan.service.dto.review.ReviewRequestDTO;
import org.pplan.service.dto.review.ReviewResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewService {

    public final ReviewMapper reviewMapper;

    public void save(ReviewRequestDTO reviewRequestDTO){
        reviewMapper.reviewSave(reviewRequestDTO);
    }

    public List<ReviewResponseDTO> reviewList(){

        return reviewMapper.reviewList();
    }

    public ReviewResponseDTO getReview(long id){
        return reviewMapper.getReview(id);
    }

    public long update(ReviewRequestDTO reviewRequestDTO) {

        return reviewMapper.reviewUpdate(reviewRequestDTO);
    }

    public long delete(long id) {
        log.info("delete.............");
        return reviewMapper.reviewDelete(id);
    }
}
