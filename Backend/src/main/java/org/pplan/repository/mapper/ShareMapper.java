package org.pplan.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.pplan.service.dto.share.ShareDTO;

@Mapper
public interface ShareMapper {

    // 좋아요 여부 확인
    Integer checkLike(ShareDTO shareDTO);

    int checkedLikeCount(ShareDTO shareDTO);
    // 좋아요 추가
    void addLike(ShareDTO shareDTO);

    // 좋아요 삭제
    void removeLike(ShareDTO shareDTO);

    // 특정 리뷰에 대한 좋아요 개수 조회
    Long getShareCount(@Param("reviewId") long reviewId);
}