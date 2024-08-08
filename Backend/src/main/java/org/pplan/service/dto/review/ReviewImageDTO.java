package org.pplan.service.dto.review;

import lombok.Data;

@Data
public class ReviewImageDTO {
    private long imageId;
    private String urlPath;
    private String sUrlPath;
    private String fullUrlPath; // 새로운 필드 추가
}
