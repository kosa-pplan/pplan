package org.pplan.service.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpDTO {
    private Long id;
    private String email;
    private String pwd;
    private String nickname;
    private String user_type;
}
