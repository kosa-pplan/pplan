package org.pplan.controller.User;

import lombok.RequiredArgsConstructor;
import org.pplan.service.User.UserService;
import org.pplan.service.dto.User.UserLoginDTO;
import org.pplan.service.dto.User.UserSignUpDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 로그인 API
    @PostMapping("/sign-up")
    public ResponseEntity<UserSignUpDTO> registerUser(@RequestBody UserSignUpDTO user) {
        UserSignUpDTO registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    // 이메일 중복 확인 API
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> signUpCheckEmail(@RequestParam String email) {
        boolean exists = userService.signUpCheckEmailExists(email);
        return ResponseEntity.ok(exists);
    }

    // 닉네임 중복 확인 API
    @GetMapping("/check-nickname")
    public ResponseEntity<Boolean> signUpCheckNickname(@RequestParam String nickname) {
        boolean exists = userService.signUpCheckNicknameExists(nickname);
        return ResponseEntity.ok(exists);
    }

    // 로그인 API
    @PostMapping("/login")
    public UserLoginDTO loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.login(userLoginDTO.getEmail(), userLoginDTO.getPwd());
    }

}
