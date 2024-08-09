package org.pplan.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.pplan.service.dto.User.UserDTO;

@Mapper
public interface UserMapper {

    void insertUser(UserDTO userDTO);
    UserDTO findByEmail(String email);
    UserDTO findByNickname(String Nickname);
}
