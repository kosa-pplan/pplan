package org.pplan.service.dto.share;

import lombok.Data;

@Data
public class ShareDTO {
    private long id;
    private long reviewId;
    private String userEmail;
}
