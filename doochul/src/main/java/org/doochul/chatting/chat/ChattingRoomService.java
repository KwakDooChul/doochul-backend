package org.doochul.chatting.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.doochul.chatting.chat.dto.ChattingRequest;
import org.doochul.chatting.chat.dto.ChattingResponse;
import org.doochul.chatting.chat.dto.ChattingRoomResponse;
import org.doochul.chatting.chat.dto.MessageResponse;
import org.doochul.chatting.pubsub.RedisSubscriber;
import org.doochul.domain.user.User;
import org.doochul.domain.user.UserRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ChattingRoomService {

    private static final String CHAT_ROOMS = "CHAT_ROOM";

    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisSubscriber redisSubscriber;
    private final RedisTemplate<String, Object> redisTemplate;
    private final UserRepository userRepository;
    private final ChattingRoomRepository chattingRoomRepository;
    private final ChattingRepository chattingRepository;

    private HashOperations<String, String, ChattingRoomResponse> opsHashChatRoom;
    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init() {
        opsHashChatRoom = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    public ChattingResponse createRoom(final ChattingRequest chattingRequest, final Long id) {
        final User targetUser = userRepository.findById(chattingRequest.targetId()).orElseThrow(
                () -> new IllegalArgumentException("선생님을 찾을 수 없습니다."));

        final User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        ChattingRoom chattingRoom = chattingRoomRepository.findBySenderAndReceiver(targetUser.getName(),
                user.getName());

        if (chattingRoom == null || !user.getName().equals(chattingRoom.getSender()) && !targetUser.getName()
                .equals(chattingRoom.getReceiver())) {
            final ChattingRoomResponse chattingRoomResponse = ChattingRoomResponse.create(user, targetUser);
            opsHashChatRoom.put(CHAT_ROOMS, chattingRoomResponse.getRoomId(),
                    chattingRoomResponse);
            chattingRoom = chattingRoomRepository.save(
                    new ChattingRoom(chattingRoomResponse.getId(), chattingRoomResponse.getRoomName(), user.getName(),
                            chattingRoomResponse.getRoomId(), targetUser.getName(), user));
            return new ChattingResponse(chattingRoom);
        } else {
            return new ChattingResponse(chattingRoom.getRoomId());
        }
    }

    public List<ChattingResponse> findAllRoomByUser(final Long id) {
        final User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        List<ChattingRoom> chattingRooms = chattingRoomRepository.findByUserOrReceiver(user,
                user.getName());

        List<ChattingResponse> chattingResponses = new ArrayList<>();

        for (ChattingRoom chattingRoom : chattingRooms) {
            if (user.getName().equals(chattingRoom.getSender())) {
                ChattingResponse chattingResponse = new ChattingResponse(
                        chattingRoom.getId(),
                        chattingRoom.getReceiver(),
                        chattingRoom.getRoomId(),
                        chattingRoom.getSender(),
                        chattingRoom.getReceiver());

                Chatting latestChatting = chattingRepository.findTopByRoomIdOrderByCreatedAtDesc(
                        chattingRoom.getRoomId());
                if (latestChatting != null) {
                    chattingResponse.setLatestMessageCreatedAt(latestChatting.getCreatedAt());
                    chattingResponse.setLatestMessageContent(latestChatting.getMessage());
                }
                chattingResponses.add(chattingResponse);
            } else {
                ChattingResponse chattingResponse = new ChattingResponse(
                        chattingRoom.getId(),
                        chattingRoom.getSender(),
                        chattingRoom.getRoomId(),
                        chattingRoom.getSender(),
                        chattingRoom.getReceiver());

                Chatting latestChatting = chattingRepository.findTopByRoomIdOrderByCreatedAtDesc(
                        chattingRoom.getRoomId());
                if (latestChatting != null) {
                    chattingResponse.setLatestMessageCreatedAt(latestChatting.getCreatedAt());
                    chattingResponse.setLatestMessageContent(latestChatting.getMessage());
                }

                chattingResponses.add(chattingResponse);
            }
        }
        return chattingResponses;
    }

    public ChattingRoomResponse findRoom(final String roomId, final Long id) {
        ChattingRoom chattingRoom = chattingRoomRepository.findByRoomId(roomId);

        final String receiver = chattingRoom.getReceiver();

        final User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        chattingRoom = chattingRoomRepository.findByRoomIdAndUserOrRoomIdAndReceiver(roomId, user, roomId,
                receiver);

        if (chattingRoom == null) {
            throw new IllegalArgumentException("쪽지방이 존재하지 않습니다.");
        }

        return new ChattingRoomResponse(
                chattingRoom.getId(),
                chattingRoom.getRoomName(),
                chattingRoom.getRoomId(),
                chattingRoom.getSender(),
                chattingRoom.getReceiver());
    }

    public MessageResponse deleteRoom(Long roomId, Long id) {
        final User user = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        ChattingRoom chattingRoom = chattingRoomRepository.findByIdAndUserOrIdAndReceiver(roomId, user, id,
                user.getName());

        if (user.getName().equals(chattingRoom.getSender())) {
            chattingRoomRepository.delete(chattingRoom);
            opsHashChatRoom.delete(CHAT_ROOMS, chattingRoom.getRoomId());
        } else if (user.getName().equals(chattingRoom.getReceiver())) {
            chattingRoom.setReceiver("존재하지 않습니다.");
            chattingRoomRepository.save(chattingRoom);
        }
        return new MessageResponse("쪽지방을 삭제했습니다.", HttpStatus.OK.value());
    }

    public void enterChatRoom(String roomId) {
        ChannelTopic topic = topics.get(roomId);
        if (topic == null) {
            topic = new ChannelTopic(roomId);
        }
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        topics.put(roomId, topic);
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }
}
