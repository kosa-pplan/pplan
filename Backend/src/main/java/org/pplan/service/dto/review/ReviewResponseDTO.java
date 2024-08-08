package org.pplan.service.dto.review;

import lombok.Data;

import java.sql.Date;

@Data
public class ReviewResponseDTO {
    private long id;
    private String title;
    private String contents;
    private Date regDate;
}
