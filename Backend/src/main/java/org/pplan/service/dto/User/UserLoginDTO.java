package org.pplan.service.dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자 로그인 시 사용하는 데이터 전송 객체 (DTO)
 * 사용자 로그인 정보를 포함하여 클라이언트와 서버 간에 데이터 교환에 사용됨
 */
// 김동혁
@Data
@NoArgsConstructor // 파라미터가 없는 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 파라미터로 받는 생성자 생성
public class UserLoginDTO {

    // 사용자의 고유 식별자
    private Long id;

    // 사용자의 이메일 주소
    private String email;

    // 사용자의 비밀번호
    private String pwd;
}
