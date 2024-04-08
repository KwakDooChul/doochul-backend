package org.doochul.chatting;


import org.doochul.chatting.dto.ChattingRequest;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChattingServiceTest {

    @Autowired
    private ChattingService chattingService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void asdf() {
        User user = User.of("2", "유선준");
        User targetUser = User.of("1", "전인표");

        userRepository.save(user);
        userRepository.save(targetUser);

        chattingService.createChatRoom(2L, new ChattingRequest(1L), "안녕하세요");
    }

}
