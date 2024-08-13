package org.pplan.service.dto.myPage;

import lombok.Data;

import java.sql.Date;

/**
 * @Author KyeongMin
 */
@Data
public class MyPageDTO {
    private int id;
    private String title;
    private Date regDate;
    private String userEmail;
}
