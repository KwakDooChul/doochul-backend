package org.doochul.ui.api;

import lombok.RequiredArgsConstructor;
import org.doochul.application.UserService;
import org.doochul.dto.UserSaveRequestDto;
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
    @PostMapping("/jojn")
    public Long save(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        return userService.회원가입(userSaveRequestDto.toEntity());
    }
}
