package org.pplan.controller.User;

import lombok.RequiredArgsConstructor;
import org.pplan.service.User.UserService;
import org.pplan.service.dto.User.UserLoginDTO;
import org.pplan.service.dto.User.UserSignUpDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 사용자 관련 API 요청을 처리하는 컨트롤러 클래스
 * 회원가입, 로그인, 회원탈퇴 등의 요청을 처리
 */
// 김동혁
@CrossOrigin // 다른 출처에서 오는 요청을 허용
@RestController // RESTful 웹 서비스의 컨트롤러로 사용
@RequiredArgsConstructor // 모든 final 필드를 포함하는 생성자를 자동으로 생성
public class UserController {

    private final UserService userService;

    /**
     * 사용자 회원가입을 처리하는 엔드포인트
     * @param user 회원가입 요청을 담은 DTO
     * @return 회원가입된 사용자 정보를 포함한 ResponseEntity
     */
    @PostMapping("/sign-up")
    public ResponseEntity<UserSignUpDTO> registerUser(@RequestBody UserSignUpDTO user) {
        UserSignUpDTO registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    /**
     * 회원가입 시 이메일 중복 확인을 처리하는 엔드포인트
     * @param email 중복 확인할 이메일
     * @return 이메일이 존재하면 true, 존재하지 않으면 false를 반환하는 ResponseEntity
     */
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> signUpCheckEmail(@RequestParam String email) {
        boolean exists = userService.signUpCheckEmailExists(email);
        return ResponseEntity.ok(exists);
    }

    /**
     * 회원가입 시 닉네임 중복 확인을 처리하는 엔드포인트
     * @param nickname 중복 확인할 닉네임
     * @return 닉네임이 존재하면 true, 존재하지 않으면 false를 반환하는 ResponseEntity
     */
    @GetMapping("/check-nickname")
    public ResponseEntity<Boolean> signUpCheckNickname(@RequestParam String nickname) {
        boolean exists = userService.signUpCheckNicknameExists(nickname);
        return ResponseEntity.ok(exists);
    }

    /**
     * 사용자 로그인을 처리하는 엔드포인트
     * @param userLoginDTO 로그인 요청을 담은 DTO
     * @return JWT 토큰을 포함한 ResponseEntity 또는 오류 메시지
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        String token = userService.login(userLoginDTO.getEmail(), userLoginDTO.getPwd());
        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    /**
     * 사용자 회원 탈퇴를 처리하는 엔드포인트
     * @param email 탈퇴할 사용자의 이메일
     * @return 성공 메시지를 포함한 ResponseEntity
     */
    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestParam String email) {
        userService.deleteUser(email);
        return ResponseEntity.ok("User deleted successfully");
    }
}
