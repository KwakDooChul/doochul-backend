package org.doochul.application;

import org.doochul.domain.User;
import org.doochul.domain.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        // Act
        userService.회원가입(user);

        // Assert
        verify(encoder).encode("testPassword");
        verify(userRepository).save(any(User.class));
    }
}
