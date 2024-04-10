package org.doochul.chatting.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChattingService {

    private final RedisTemplate<String, ChatMessage> redisTemplateMessage;
    private final ChattingRepository chattingRepository;

    public void saveMessage(ChatMessage chatMessage) {
        Chatting chatting = new Chatting(chatMessage.getSender(), chatMessage.getRoomId(), chatMessage.getMessage());
        chattingRepository.save(chatting);
        redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(Chatting.class));
        redisTemplateMessage.opsForList().rightPush(chatMessage.getRoomId(), chatMessage);
        redisTemplateMessage.expire(chatMessage.getRoomId(), 1, TimeUnit.MINUTES);
    }

    public List<ChatMessage> loadMessage(String roomId) {
        List<ChatMessage> chatMessages = new ArrayList<>();

        List<ChatMessage> redisMessages = redisTemplateMessage.opsForList().range(roomId, 0, 99);

        if (redisMessages == null || redisMessages.isEmpty()) {
            List<Chatting> dbChattings = chattingRepository.findTop100ByRoomIdOrderByCreatedAtAsc(roomId);
            for (Chatting chatting : dbChattings) {
                ChatMessage messageDto = new ChatMessage(chatting);
                chatMessages.add(messageDto);
                redisTemplateMessage.setValueSerializer(new Jackson2JsonRedisSerializer<>(Chatting.class));
                redisTemplateMessage.opsForList()
                        .rightPush(roomId, messageDto);
            }
        } else {
            chatMessages.addAll(redisMessages);
        }
        return chatMessages;
    }
}
