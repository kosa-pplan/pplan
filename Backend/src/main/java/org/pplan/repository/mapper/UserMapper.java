package org.pplan.repository.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.pplan.service.dto.User.UserLoginDTO;
import org.pplan.service.dto.User.UserSignUpDTO;

@Mapper
public interface UserMapper {

    void insertUser(UserSignUpDTO userSignUpDTO);
    UserSignUpDTO signUpFindByEmail(String email);
    UserSignUpDTO signUpFindByNickname(String nickname);

    UserLoginDTO loginFindByEmail(String email);
}
