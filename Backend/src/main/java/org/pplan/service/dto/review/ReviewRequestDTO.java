package org.pplan.service.dto.review;

import lombok.Data;

@Data
public class ReviewRequestDTO {
    private long id;
    private String title;
    private String contents;
}
