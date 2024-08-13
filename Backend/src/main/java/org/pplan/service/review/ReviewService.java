package org.pplan.service.review;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.pplan.repository.mapper.ReviewMapper;
import org.pplan.service.dto.myPage.MyPageDTO;
import org.pplan.service.dto.review.ReviewDTO;
import org.pplan.service.dto.review.ReviewImageDTO;
import org.pplan.service.dto.review.ReviewListDTO;
import org.pplan.service.dto.review.ReviewWriterCheckDTO;
import org.pplan.service.image.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 리뷰 관련 비즈니스 로직을 처리하는 서비스 클래스
 * @Author Kyeongmin
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final ImageService imageService;

    /**
     * 리뷰를 저장합니다. 리뷰와 관련된 이미지를 함께 저장합니다.
     * 이 메소드는 트랜잭션이 적용되어 모든 데이터베이스 작업이 원자적으로 처리됩니다.
     * @param reviewDTO 저장할 리뷰 데이터 전송 객체
     * @return 저장된 리뷰 데이터 전송 객체
     */
    @Transactional
    public ReviewDTO saveReview(ReviewDTO reviewDTO) {
        // 리뷰 저장
        reviewMapper.insertReview(reviewDTO);
        //저장된 리뷰ID 확인
        Long reviewId = reviewDTO.getId();

        //자식테이블에 이미지 저장
        saveReviewImages(reviewDTO.getReviewImageDTOList(), reviewId);

        //Course의 글작성 상태에 NO->YES 업데이트
        reviewMapper.updateYesCourseStatus(reviewDTO.getCourseId());

        return reviewDTO;
    }

    /**
     * 리뷰에 첨부된 이미지를 저장합니다.
     * @param imageDTOList 이미지 데이터 전송 객체 리스트
     * @param reviewId 리뷰 ID
     */
    private void saveReviewImages(List<ReviewImageDTO> imageDTOList, Long reviewId) {
        for (ReviewImageDTO imageDTO : imageDTOList) {
            reviewMapper.insertReviewImage(
                    imageDTO.getUrlPath(),
                    imageDTO.getSUrlPath(),
                    reviewId
            );
        }
    }

    /**
     * 모든 리뷰 목록을 가져옵니다.
     * @return 리뷰 목록
     */
    public List<ReviewListDTO> reviewList() {
        return reviewMapper.reviewList();
    }

    /**
     * 상세 리뷰를 가져옵니다.
     * @param id 리뷰 ID
     * @return 리뷰 데이터 전송 객체
     */
    public ReviewDTO getReview(long id) {
        return reviewMapper.getReview(id);
    }

    /**
     * 리뷰를 업데이트합니다.
     * 이 메소드는 트랜잭션이 적용되어 리뷰 업데이트가 원자적으로 처리됩니다.
     * @param reviewDTO 업데이트할 리뷰 데이터 전송 객체
     */
    @Transactional
    public void update(ReviewDTO reviewDTO) {
        long rowsAffected = reviewMapper.reviewUpdate(reviewDTO);
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("Review not found or update failed.");
        }
    }

    /**
     * 특정 리뷰를 삭제합니다.
     * 이 메소드는 트랜잭션이 적용되어 리뷰 삭제가 원자적으로 처리됩니다.
     * @param id 삭제할 리뷰 ID
     */
    @Transactional
    public void delete(long id) {
        log.info("Deleting review with id: {}", id);
        long rowsAffected = reviewMapper.reviewDelete(id);
        if (rowsAffected == 0) {
            throw new IllegalArgumentException("Review not found or delete failed.");
        }
    }

    /**
     * 사용자가 좋아요를 누른 리뷰 목록을 가져옵니다.
     * @param userEmail 사용자 이메일
     * @return 사용자가 좋아요를 누른 리뷰 목록
     */
    public List<MyPageDTO> getMyLikeReview(String userEmail) {
        return reviewMapper.getMyLikeReview(userEmail);
    }

    /**
     * 업로드된 이미지를 처리하여 리뷰 이미지 DTO 리스트를 생성합니다.
     * @param images 이미지 파일 배열
     * @return 처리된 리뷰 이미지 DTO 리스트
     * @throws IOException 이미지 처리 중 오류 발생 시
     */
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

    /**
     * 특정 과정의 작성자 이메일을 가져옵니다.
     * @param courseId 과정 ID
     * @return ReviewWriterCheckDTO(작성자 및 작성여부)
     */
    public ReviewWriterCheckDTO getAuthorCheckYNInfoByCourseId(Long courseId) {
        return reviewMapper.getAuthorCheckYNInfoByCourseId(courseId);
    }

    /**
     * 특정 리뷰 ID의 존재 여부를 확인합니다.
     * @param reviewId 리뷰 ID
     * @return 리뷰의 존재 여부 (1: 존재함, 0: 존재하지 않음)
     */
    public int checkCountReviewId(Long reviewId) {
        return reviewMapper.checkCountReviewId(reviewId);
    }
}