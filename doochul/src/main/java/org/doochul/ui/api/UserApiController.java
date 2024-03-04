package org.doochul.ui.api;

import lombok.RequiredArgsConstructor;
import org.doochul.application.UserService;
import org.doochul.auth.PrincipalDetail;
import org.doochul.domain.user.User;
import org.doochul.dto.ResponseDto;
import org.doochul.dto.UserSaveRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    /**
     *  회원가입 API
     */
    @PostMapping("/join")
    public Long save(UserSaveRequestDto userSaveRequestDto) { //@RequestBody
        return userService.save(userSaveRequestDto.toEntity());
    }

    /**
     * 회원수정 API
     */
    @PutMapping("/edit")
    public Long update(User user, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        userService.update(user);
        return user.getId();
    }
}
