package org.pplan.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.pplan.service.dto.review.ReviewDTO;

import java.util.List;

@Mapper
public interface ReviewMapper {
    void insertReview(ReviewDTO reviewDTO);

    long selectInsertId(long userId);

    void insertReviewImage(String urlPath, String sUrlPath, long reviewId);

    List<ReviewDTO> reviewList();

    ReviewDTO getReview(long id);

    long reviewUpdate(ReviewDTO reviewRequestDTO);

    long reviewDelete(long id);

}
