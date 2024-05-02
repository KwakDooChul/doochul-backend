package org.doochul.chatting.chat;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.doochul.chatting.pubsub.RedisPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class ChattingController {

    private final RedisPublisher redisPublisher;
    private final ChattingRoomService chattingRoomService;
    private final ChattingService chattingService;

    @MessageMapping("/chat/message")
    public void message(ChatMessage chatMessage) {
        chattingRoomService.enterChatRoom(chatMessage.getRoomId());
        redisPublisher.publish(chattingRoomService.getTopic(chatMessage.getRoomId()), chatMessage);
        chattingService.saveMessage(chatMessage);
    }

    @GetMapping("/chat/room/{roomId}/message")
    public ResponseEntity<List<ChatMessage>> loadMessage(@PathVariable String roomId) {
        return ResponseEntity.ok(chattingService.loadMessage(roomId));
    }
}
