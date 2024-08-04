package org.doochul.ui;

import lombok.RequiredArgsConstructor;
import org.doochul.application.UserService;
import org.doochul.common.resolver.AuthenticationPrincipal;
import org.doochul.ui.dto.UserInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<UserInfoResponse> readUserInfo(@AuthenticationPrincipal final Long userId) {
        final UserInfoResponse userInfoResponse = userService.findUserInfo(userId);
        return ResponseEntity.ok(userInfoResponse);
    }
}
