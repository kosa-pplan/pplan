package org.pplan.service.dto.review;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class ReviewDTO {
    private Long id;
    private String title;
    private String contents;
    private List<ReviewImageDTO> reviewImageDTOList;
    private Date regDate;

}
