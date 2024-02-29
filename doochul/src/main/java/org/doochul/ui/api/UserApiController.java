package org.doochul.ui.api;

import lombok.RequiredArgsConstructor;
import org.doochul.application.UserService;
import org.doochul.domain.User;
import org.doochul.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    /**
     *  회원가입 API
     */
    @PostMapping("/auth/api/v1/user")
    public ResponseDto<Integer> save(@RequestBody User user) {
        userService.회원가입(user);
        return new ResponseDto<>(HttpStatus.OK.value(),1); // JSON으로 변환해서 리턴
    }
}
