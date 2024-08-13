package org.pplan.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.pplan.service.dto.share.ShareDTO;

/**
 * 좋아요 관련 데이터베이스 작업을 위한 MyBatis 매퍼 인터페이스
 * @Author Kyeongmin
 */
@Mapper
public interface ShareMapper {

    /**
     * 특정 사용자와 리뷰에 대한 좋아요 여부를 확인합니다.
     * @param shareDTO 사용자 이메일과 리뷰 ID를 포함하는 데이터 전송 객체
     * @return 좋아요 여부 (1: 좋아요, 0: 좋아요 아님)
     */
    Integer checkLike(ShareDTO shareDTO);

    /**
     * 특정 사용자와 리뷰에 대한 좋아요 개수를 확인합니다.
     * @param shareDTO 사용자 이메일과 리뷰 ID를 포함하는 데이터 전송 객체
     * @return 좋아요 개수
     */
    int checkedLikeCount(ShareDTO shareDTO);

    /**
     * 사용자가 특정 리뷰에 좋아요를 추가합니다.
     * @param shareDTO 사용자 이메일과 리뷰 ID를 포함하는 데이터 전송 객체
     */
    void addLike(ShareDTO shareDTO);

    /**
     * 사용자가 특정 리뷰에 대한 좋아요를 삭제합니다.
     * @param shareDTO 사용자 이메일과 리뷰 ID를 포함하는 데이터 전송 객체
     */
    void removeLike(ShareDTO shareDTO);
}