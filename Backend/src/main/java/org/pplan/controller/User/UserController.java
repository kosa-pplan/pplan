package org.pplan.controller.User;

import lombok.RequiredArgsConstructor;
import org.pplan.service.User.UserService;
import org.pplan.service.dto.User.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO user) {
        UserDTO registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }
}
