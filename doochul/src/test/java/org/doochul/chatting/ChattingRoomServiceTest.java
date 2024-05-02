package org.doochul.chatting;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.doochul.chatting.chat.ChattingRoomService;
import org.doochul.chatting.chat.dto.ChattingRequest;
import org.doochul.chatting.chat.dto.ChattingResponse;
import org.doochul.chatting.chat.dto.ChattingRoomResponse;
import org.doochul.chatting.chat.dto.MessageResponse;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChattingRoomServiceTest {

    @Autowired
    private ChattingRoomService chattingRoomService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 방_생성_테스트() {
        //given
        User user = User.ofGeneral(1L, "유선준학생");
        User teacher = User.ofTeacher(2L, "전인표선생");

        userRepository.save(user);
        userRepository.save(teacher);

        //when
        ChattingResponse response = chattingRoomService.createRoom(new ChattingRequest(2L), 1L);

        //then
        assertNotNull(response);

        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(response);
            System.out.println(jsonResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void 방_전체_조회_테스트() {
        //given
        User user = User.ofGeneral(1L, "유선준학생");
        User teacher = User.ofTeacher(2L, "전인표선생");

        userRepository.save(user);
        userRepository.save(teacher);

        chattingRoomService.createRoom(new ChattingRequest(2L), 1L);
        //when
        List<ChattingResponse> response = chattingRoomService.findAllRoomByUser(1L);

        //then
        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(response);
            System.out.println(jsonResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void 방_하나_조회_테스트() {
        User user = User.ofGeneral(1L, "유선준학생");
        User teacher = User.ofTeacher(2L, "전인표선생");

        userRepository.save(user);
        userRepository.save(teacher);

        ChattingResponse chatting = chattingRoomService.createRoom(new ChattingRequest(2L), 1L);

        //when
        ChattingRoomResponse response = chattingRoomService.findRoom(chatting.getRoomId(), 1L);
        //then
        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(response);
            System.out.println(jsonResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    void 방_삭제_테스트() {
        //given
        User user = User.ofGeneral(1L, "유선준학생");
        User teacher = User.ofTeacher(2L, "전인표선생");

        userRepository.save(user);
        userRepository.save(teacher);

        chattingRoomService.createRoom(new ChattingRequest(2L), 1L);
        //when
        MessageResponse messageResponse = chattingRoomService.deleteRoom(1L, 1L);
        //then
        try {
            String jsonResponse = new ObjectMapper().writeValueAsString(messageResponse);
            System.out.println(jsonResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
