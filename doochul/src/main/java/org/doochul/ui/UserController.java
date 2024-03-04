package org.doochul.ui;

import lombok.RequiredArgsConstructor;
import org.doochul.application.UserService;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;


    /**
     * 회원가입 페이지
     */
    @GetMapping("/join")
    public String userJoin() {
        return "user-join";
    }

    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public String userLogin() {
        return "user-login";
    }

    /**
     *  아이디 중복 체크
     */
    @GetMapping("/user/username/exists")
    public ResponseEntity<String> checkUsernameDuplicate(@RequestParam(value = "username") String username) {
        boolean isDuplicate = userService.checkUsernameDuplicate(username);
        if (isDuplicate) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용할 수 없는 아이디입니다.");
        } else {
            return ResponseEntity.ok("사용할 수 있는 아이디입니다.");
        }
    }

}
