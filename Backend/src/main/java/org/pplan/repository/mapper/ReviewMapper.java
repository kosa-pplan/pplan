package org.pplan.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.pplan.service.dto.review.ReviewRequestDTO;
import org.pplan.service.dto.review.ReviewResponseDTO;

import java.util.List;

@Mapper
public interface ReviewMapper {
    void reviewSave(ReviewRequestDTO reviewRequestDTO);

    List<ReviewResponseDTO> reviewList();

    ReviewResponseDTO getReview(long id);

    long reviewUpdate(ReviewRequestDTO reviewRequestDTO);

    long reviewDelete(long id);
}
