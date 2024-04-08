package org.doochul.chatting;

import org.doochul.chatting.pubsub.RedisPublisher;
import org.doochul.chatting.pubsub.RedisSubscriber;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.listener.ChannelTopic;

@SpringBootTest
class RedisPubSubTest {

    @Autowired
    private RedisPublisher redisPublisher;

    @Test
    void testRedisPublisher() {
        ChannelTopic topic = new ChannelTopic("CHAT_ROOM");

        ChatMessage chatMessage = new ChatMessage();

        chatMessage.setMessage("안녕하세요");

        redisPublisher.publish(topic, chatMessage);
    }
}
