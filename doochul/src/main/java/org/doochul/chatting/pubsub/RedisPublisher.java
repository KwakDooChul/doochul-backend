package org.doochul.chatting.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.doochul.chatting.ChatMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPublisher {

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public void publish(ChannelTopic topic, ChatMessage message) {
        try {
            String messageName = objectMapper.writeValueAsString(message);
            redisTemplate.convertAndSend(topic.getTopic(), messageName);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
