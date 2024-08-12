package org.pplan.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.pplan.service.dto.myPage.MyPageDTO;
import org.pplan.service.dto.review.ReviewDTO;
import org.pplan.service.dto.review.ReviewListDTO;

import java.util.List;

@Mapper
public interface ReviewMapper {

    List<MyPageDTO> getMyLikeReview(String userEmail);


    void insertReview(ReviewDTO reviewDTO);

    void insertReviewImage(String urlPath, String sUrlPath, long reviewId);

    List<ReviewListDTO> reviewList();

    ReviewDTO getReview(long id);

    long reviewUpdate(ReviewDTO reviewRequestDTO);

    long reviewDelete(long id);

}
