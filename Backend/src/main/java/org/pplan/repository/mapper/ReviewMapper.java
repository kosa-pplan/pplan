package org.pplan.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.pplan.service.dto.myPage.MyPageDTO;
import org.pplan.service.dto.review.ReviewDTO;
import org.pplan.service.dto.review.ReviewListDTO;
import org.pplan.service.dto.review.ReviewWriterCheckDTO;

import java.util.List;
import java.util.Map;

/**
 * 리뷰 관련 데이터베이스 작업을 위한 MyBatis 매퍼 인터페이스
 * @Author Kyeongmin
 */
@Mapper
public interface ReviewMapper {

    /**
     * 사용자가 좋아요를 누른 리뷰 목록을 가져옵니다.
     * @param userEmail 사용자 이메일
     * @return 사용자가 좋아요를 누른 리뷰 목록
     */
    List<MyPageDTO> getMyLikeReview(String userEmail);

    /**
     * 특정 과정의 상태를 "예정"으로 업데이트합니다.
     * @param courseId 과정 ID
     */
    void updateYesCourseStatus(long courseId);

    /**
     * 새로운 리뷰를 데이터베이스에 삽입합니다.
     * @param reviewDTO 리뷰 데이터 전송 객체
     */
    void insertReview(ReviewDTO reviewDTO);

    /**
     * 리뷰 이미지 정보를 데이터베이스에 삽입합니다.
     * @param urlPath 이미지 URL
     * @param sUrlPath 썸네일 이미지 URL
     * @param reviewId 리뷰 ID
     */
    void insertReviewImage(String urlPath, String sUrlPath, long reviewId);

    /**
     * 모든 리뷰 목록을 가져옵니다.
     * @return 리뷰 목록
     */
    List<ReviewListDTO> reviewList(Map<String, Object> params);

    /**
     * 특정 리뷰를 가져옵니다.
     * @param id 리뷰 ID
     * @return 리뷰 데이터 전송 객체
     */
    ReviewDTO getReview(long id);

    /**
     * 리뷰를 업데이트합니다.
     * @param reviewRequestDTO 업데이트할 리뷰 데이터 전송 객체
     * @return 업데이트된 리뷰의 ID
     */
    long reviewUpdate(ReviewDTO reviewRequestDTO);

    /**
     * 특정 리뷰를 삭제합니다.
     * @param id 삭제할 리뷰의 ID
     * @return 삭제된 리뷰의 ID
     */
    long reviewDelete(long id);

    /**
     * 글을 작성할 때 코스 작성자가 맞는지 확인 -> 작성자 이메일을 찾습니다.
     * @param courseId 과정 ID
     * @return 작성자 이메일
     */
    String findByCreatorEmail(Long courseId);

    /**
     * 특정 리뷰 ID의 존재 여부를 확인합니다.
     * @param reviewId 리뷰 ID
     * @return 리뷰 ID의 존재 여부 (0: 존재하지 않음, 1: 존재함)
     */
    int checkCountReviewId(long reviewId);

    ReviewWriterCheckDTO getAuthorCheckYNInfoByCourseId(long courseId);

    long getTotalReviewCount();
}