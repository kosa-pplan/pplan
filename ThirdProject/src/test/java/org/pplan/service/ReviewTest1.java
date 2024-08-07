package org.pplan.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.pplan.service.dto.review.ReviewRequestDTO;
import org.pplan.service.dto.review.ReviewResponseDTO;
import org.pplan.service.review.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReviewTest1 {

    @Autowired
    private ReviewService reviewService;

    @Test
    public void insertTest(){
        ReviewRequestDTO reviewRequestDTO = new ReviewRequestDTO();
        reviewRequestDTO.setContents("dkgjkladsgjlkdasjglkdsajvlkcnlvknwdaslk;gnklds;agnlk;sdangvl;");
        reviewRequestDTO.setTitle("안녕하세요 만나서 반갑습니다.");
        reviewService.save(reviewRequestDTO);
    }
    @Test
    public void ListTest(){

        for(ReviewResponseDTO reviewResponseDTO : reviewService.reviewList()){
            System.out.println("######List"+reviewResponseDTO.toString());
        }
    }

    @Test
    public void getUpdate(){

        ReviewRequestDTO reviewRequestDTO = new ReviewRequestDTO() ;
        //실행전 존재하는 번호인지 확인
        reviewRequestDTO.setId(2);
        reviewRequestDTO.setTitle("서비스 제목");
        reviewRequestDTO.setContents("service 수정된 내용");
        long count = reviewService.update(reviewRequestDTO);
        System.out.println("Update COUNT : " + count);
    }


}
