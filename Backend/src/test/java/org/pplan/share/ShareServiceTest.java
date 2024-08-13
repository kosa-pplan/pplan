package org.pplan.share;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pplan.repository.mapper.ShareMapper;
import org.pplan.service.share.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.pplan.service.dto.share.ShareDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
public class ShareServiceTest {

    @Autowired
    private ShareService shareService;

    @Autowired
    private ShareMapper shareMapper;

    private ShareDTO shareDTO;

    @BeforeEach
    public void setUp() {
        // 테스트 데이터 초기화
        shareDTO = new ShareDTO();
        shareDTO.setReviewId(2L);
        shareDTO.setUserEmail("momo@naver.com");
    }
//
//    @Test
//    public void testAddLike() {
//        shareService.addShare(shareDTO);
//        Long likeId = shareMapper.checkLike(shareDTO);
//        assertEquals(1, likeId); // 추가된 좋아요가 있어야 함
//    }

//    @Test
//    public void testRemoveLike() {
//        shareService.removeShare(shareDTO); // 제거
//        System.out.println(shareDTO.getReviewId());
//        System.out.println(shareDTO.getUserId());
//        shareService.addShare(shareDTO);
//        long likeCount = shareService.getShareCount(shareDTO.getReviewId());
//        System.out.println("likeCount"+likeCount);
//    }

//    @Test
//    public void testGetLikeCount() {
//        long likeCount = shareService.getShareCount(shareDTO.getReviewId());
//        System.out.println(likeCount);
//    }
}