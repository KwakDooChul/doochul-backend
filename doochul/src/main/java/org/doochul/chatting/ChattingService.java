package org.doochul.chatting;

import lombok.RequiredArgsConstructor;
import org.doochul.chatting.dto.ChattingRequest;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChattingService {

    private final ChattingRepository chattingRepository;
    private final UserRepository userRepository;

    public List<ChattingRoom> findAllRooms() {
        return chattingRepository.findAllRoom();
    }

    public ChattingRoom findRoomById(final String id) {
        return chattingRepository.findRoomById(id);
    }

    public ChattingRoom createChatRoom(final Long userId, final ChattingRequest chattingRequest, final String name) {
        User user = userRepository.findById(userId).orElseThrow();
        User targetUser = userRepository.findById(chattingRequest.id()).orElseThrow();
        ChattingRoom chattingRoom = ChattingRoom.create(user, targetUser, name);
        return chattingRepository.createChatRoom(chattingRoom);
    }

    public void enterChatRoom(final String roomId) {
        chattingRepository.enterChatRoom(roomId);
    }

    public ChannelTopic getTopic(final String roomId) {
        return chattingRepository.getTopic(roomId);
    }
}
