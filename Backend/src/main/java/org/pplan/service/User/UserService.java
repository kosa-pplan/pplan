package org.pplan.service.User;

import lombok.RequiredArgsConstructor;
import org.pplan.repository.mapper.UserMapper;
import org.pplan.service.dto.User.UserLoginDTO;
import org.pplan.service.dto.User.UserSignUpDTO;
import org.pplan.util.JwtUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    // 회원 가입 기능
    public UserSignUpDTO registerUser(UserSignUpDTO userSignUpDTO) {
        userMapper.insertUser(userSignUpDTO);
        return userSignUpDTO;
    }

    // 회원 가입 이메일 중복 확인 메서드
    public boolean signUpCheckEmailExists(String email) {
        return userMapper.signUpFindByEmail(email) != null;
    }

    // 회원 가입 닉네임 중복 확인 메서드
    public boolean signUpCheckNicknameExists(String nickname) {
        return userMapper.signUpFindByNickname(nickname) != null;
    }

    // 로그인 기능
    public String login(String email, String pwd) {
        UserLoginDTO userLoginDTO = userMapper.loginFindByEmail(email);
        if (userLoginDTO != null && userLoginDTO.getPwd().equals(pwd)) {
            // Generate JWT Token
            return jwtUtil.generateToken(email);
        } else {
            return null;
        }
    }

    // 회원 탈퇴 기능
    public void deleteUser(String email) {
        userMapper.deleteUserByEmail(email);
    }
}
