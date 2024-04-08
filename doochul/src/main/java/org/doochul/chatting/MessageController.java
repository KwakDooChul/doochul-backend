package org.doochul.chatting;

import lombok.RequiredArgsConstructor;
import org.doochul.chatting.pubsub.RedisPublisher;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class MessageController {

    private final RedisPublisher redisPublisher;
    private final ChattingService chattingService;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (MessageType.ENTER.equals(message.getType())) {
            chattingService.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender().getName() + "님이 입장하셨습니다.");
        }
        redisPublisher.publish(chattingService.getTopic(message.getRoomId()), message);
    }
}
