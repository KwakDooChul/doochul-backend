package org.doochul.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.doochul.ui.dto.UserInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @DisplayName("유저의 정보를 조회한다.")
    @Test
    void findUserInfo() {
        //given
        final Long userId = 1L;
        final Long socialId = 1L;
        final String socialType = "kakao";
        final String name = "테스트 유저 1";
        final User user = User.of(userId, socialId, socialType, name);

        given(userRepository.getById(userId)).willReturn(user);

        //when
        final UserInfoResponse userInfo = userService.findUserInfo(userId);

        //then
        assertThat(userInfo.socialId()).isEqualTo(socialId);
        assertThat(userInfo.socialType()).isEqualTo(socialType);
        assertThat(userInfo.name()).isEqualTo("테스트 유저 1");
    }
}
