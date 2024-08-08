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
        if (userMapper.findByEmail(userDTO.getEmail()) != null) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }
        userMapper.insertUser(userDTO);
        return userDTO;
    }
}
