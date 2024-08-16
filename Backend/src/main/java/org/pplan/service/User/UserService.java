package org.pplan.service.User;

import lombok.RequiredArgsConstructor;
import org.pplan.repository.mapper.UserMapper;
import org.pplan.service.dto.User.UserLoginDTO;
import org.pplan.service.dto.User.UserSignUpDTO;
import org.pplan.util.JwtUtil;
import org.springframework.stereotype.Service;

/**
 * 사용자 관련 서비스 로직을 처리하는 클래스
 * 회원 가입, 로그인, 회원 탈퇴 등의 기능을 제공
 */
// 김동혁
@Service
@RequiredArgsConstructor // 모든 final 필드를 포함하는 생성자를 자동 생성
public class UserService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    /**
     * 사용자 회원가입을 처리하는 메서드
     * @param userSignUpDTO 회원가입 정보를 담고 있는 DTO
     * @return 회원가입이 완료된 사용자의 정보
     */
    public UserSignUpDTO registerUser(UserSignUpDTO userSignUpDTO) {
        userMapper.insertUser(userSignUpDTO);
        return userSignUpDTO;
    }

    /**
     * 회원가입 시 이메일 중복 여부를 확인하는 메서드
     * @param email 확인할 이메일 주소
     * @return 이메일이 이미 존재하면 true, 아니면 false
     */
    public boolean signUpCheckEmailExists(String email) {
        return userMapper.signUpFindByEmail(email) != null;
    }

    /**
     * 회원가입 시 닉네임 중복 여부를 확인하는 메서드
     * @param nickname 확인할 닉네임
     * @return 닉네임이 이미 존재하면 true, 아니면 false
     */
    public boolean signUpCheckNicknameExists(String nickname) {
        return userMapper.signUpFindByNickname(nickname) != null;
    }

    /**
     * 사용자의 로그인 처리 메서드
     * @param email 로그인할 사용자의 이메일
     * @param pwd 로그인할 사용자의 비밀번호
     * @return 로그인 성공 시 JWT 토큰, 실패 시 null
     */
    public String login(String email, String pwd) {
        UserLoginDTO userLoginDTO = userMapper.loginFindByEmail(email);
        if (userLoginDTO != null && userLoginDTO.getPwd().equals(pwd)) {
            // JWT 토큰 생성
            return jwtUtil.generateToken(email);
        } else {
            return null;
        }
    }

    /**
     * 사용자의 회원 탈퇴 처리 메서드
     * @param email 탈퇴할 사용자의 이메일
     */
    public void deleteUser(String email) {
        userMapper.deleteUserByEmail(email);
    }
}
