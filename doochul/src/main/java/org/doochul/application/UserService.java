package org.doochul.application;

import lombok.RequiredArgsConstructor;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.UserInfoResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserInfoResponse findUserInfo(final Long userId) {
       final User user = userRepository.getById(userId);
        return UserInfoResponse.from(user);
    }
}
