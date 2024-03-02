package org.doochul.application;

import org.doochul.domain.User;
import org.doochul.domain.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void test회원가입() {
        // Arrange
        User user1 = new User();
        user1.setUsername("회원");

        User user2 = new User();
        user2.setUsername("회원");


        // Act
        /*userService.회원가입(user1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> userService.회원가입(user2)); //assertThrows : 예외 처리 메서드, ()->가 실행되고 예외가 발생해야 한다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");*/

        // Assert
        //verify(encoder).encode("testPassword");
        //verify(userRepository).save(any(User.class));

    }
}
