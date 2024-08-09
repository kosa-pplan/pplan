package org.pplan.service.User;

import lombok.RequiredArgsConstructor;
import org.pplan.repository.mapper.UserMapper;
import org.pplan.service.dto.User.UserDTO;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public UserDTO registerUser(UserDTO userDTO) {
        userMapper.insertUser(userDTO);
        return userDTO;
    }

    // 이메일 중복 확인 메서드
    public boolean checkEmailExists(String email) {
        return userMapper.findByEmail(email) != null;
    }

    // 닉네임 중복 확인 메서드
    public boolean checkNicknameExists(String nickname) {
        return userMapper.findByNickname(nickname) != null;
    }
}
