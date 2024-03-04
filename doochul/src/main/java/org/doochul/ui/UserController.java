package org.doochul.ui;

import lombok.RequiredArgsConstructor;
import org.doochul.application.UserService;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * 유저 조인 페이지
     */
    @GetMapping("/user")
    public String user() {
        return "user";
    }
//    @GetMapping("/join")
//    public String hello() {
//        return "<h1>hello</h1>";
//    }

//    @Autowired
//    private UserRepository userRepository;
//
//    @PostMapping("/join")
//    public String join(User user) { // key=value (약속된 규칙)
//        System.out.println("user: " + user);
//        System.out.println("username: " + user.getUsername());
//        System.out.println("password: " + user.getPassword());
//        userRepository.save(user);
//        return "회원가입이 완료되었습니다.";
//    }
//
//    @GetMapping("/join/{id}")
//    public User detail(@PathVariable Long id) {
//        User user = userRepository.findById(id).orElseThrow(() -> {
//            return new IllegalArgumentException("해당 사용자는 없습니다.");
//        });
//
//        return user;
//    }
}
