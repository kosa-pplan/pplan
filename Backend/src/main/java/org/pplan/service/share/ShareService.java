package org.pplan.service.share;

import lombok.RequiredArgsConstructor;
import org.pplan.repository.mapper.ShareMapper;
import org.pplan.service.dto.share.ShareDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShareService {

    public final ShareMapper shareMapper;

    @Transactional
    public void toggleLike(ShareDTO shareDTO) {
        // 좋아요 여부 확인
        Integer existingLike = shareMapper.checkLike(shareDTO);
        System.out.println("existingLike"+existingLike);
        if (existingLike !=null && existingLike > 0) {
            // 좋아요가 이미 존재하면 삭제
            shareMapper.removeLike(shareDTO);
        } else {
            // 좋아요가 없으면 추가
            shareMapper.addLike(shareDTO);
        }
    }
    public long getShareCount(long reviewId) {
        return shareMapper.getShareCount(reviewId);
    }


    public boolean checkLikeCount(ShareDTO shareDTO){
        int count = shareMapper.checkedLikeCount(shareDTO);
        System.out.println(count+"count");
        return  count >0;
    }

}
