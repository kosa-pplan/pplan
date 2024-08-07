package org.pplan.service.dto.review;

import lombok.Data;

import java.util.List;

@Data
public class ReviewRequestDTO {
    private long id;
    private String title;
    private String contents;
}
