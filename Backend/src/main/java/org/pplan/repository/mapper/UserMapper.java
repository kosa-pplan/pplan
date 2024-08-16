package org.pplan.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.pplan.service.dto.User.UserLoginDTO;
import org.pplan.service.dto.User.UserSignUpDTO;

/**
 * MyBatis 매퍼 인터페이스
 * 데이터베이스와 상호작용하는 메서드를 정의
 */
// 김동혁
@Mapper
public interface UserMapper {

    /**
     * 사용자 정보를 데이터베이스에 삽입하는 메서드
     * @param userSignUpDTO 데이터베이스에 저장할 사용자 정보
     */
    void insertUser(UserSignUpDTO userSignUpDTO);

    /**
     * 이메일로 사용자 정보를 조회하는 메서드 (회원가입 시 이메일 중복 확인용)
     * @param email 조회할 이메일 주소
     * @return 해당 이메일을 가진 사용자 정보, 없으면 null
     */
    UserSignUpDTO signUpFindByEmail(String email);

    /**
     * 닉네임으로 사용자 정보를 조회하는 메서드 (회원가입 시 닉네임 중복 확인용)
     * @param nickname 조회할 닉네임
     * @return 해당 닉네임을 가진 사용자 정보, 없으면 null
     */
    UserSignUpDTO signUpFindByNickname(String nickname);

    /**
     * 이메일로 로그인 시 사용자 정보를 조회하는 메서드
     * @param email 로그인하려는 사용자의 이메일 주소
     * @return 해당 이메일을 가진 사용자 정보, 없으면 null
     */
    UserLoginDTO loginFindByEmail(String email);

    /**
     * 이메일로 사용자 정보를 삭제하는 메서드 (회원 탈퇴 시 사용)
     * @param email 삭제하려는 사용자의 이메일 주소
     */
    void deleteUserByEmail(String email);
}
