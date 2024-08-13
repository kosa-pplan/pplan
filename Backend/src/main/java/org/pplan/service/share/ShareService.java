package org.pplan.service.share;

import lombok.RequiredArgsConstructor;
import org.pplan.repository.mapper.ShareMapper;
import org.pplan.service.dto.share.ShareDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 공유 및 좋아요 관련 비즈니스 로직을 처리하는 서비스 클래스
 * @Author Kyeongmin
 */
@Service
@RequiredArgsConstructor
public class ShareService {

    private final ShareMapper shareMapper;

    /**
     * 사용자의 좋아요를 토글합니다.
     * 좋아요가 이미 존재하면 삭제하고, 그렇지 않으면 추가합니다.
     * 이 메소드는 트랜잭션이 적용되어 좋아요 추가 및 삭제 작업이 원자적으로 처리됩니다.
     * @param shareDTO 좋아요 정보를 담고 있는 데이터 전송 객체
     */
    @Transactional
    public void toggleLike(ShareDTO shareDTO) {
        // 현재 좋아요 여부 확인
        Integer existingLike = shareMapper.checkLike(shareDTO);
        System.out.println("existingLike: " + existingLike);

        if (existingLike != null && existingLike > 0) {
            // 이미 좋아요가 존재하면 삭제
            shareMapper.removeLike(shareDTO);
        } else {
            // 좋아요가 없으면 추가
            shareMapper.addLike(shareDTO);
        }
    }

    /**
     * 특정 리뷰에 대해 사용자가 좋아요를 눌렀는지 확인합니다.
     * @param shareDTO 사용자 이메일과 리뷰 ID를 담고 있는 데이터 전송 객체
     * @return 좋아요 여부 (true: 좋아요 존재, false: 좋아요 없음)
     */
    public boolean checkLikeCount(ShareDTO shareDTO) {
        int count = shareMapper.checkedLikeCount(shareDTO);
        System.out.println(count + " count");
        return count > 0;
    }
}