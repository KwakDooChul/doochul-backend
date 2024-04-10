package org.doochul.chatting.chat;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {

    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime sendTime;

    public ChatMessage(Chatting chatting) {
        this.sender = chatting.getSender();
        this.roomId = chatting.getRoomId();
        this.message = chatting.getMessage();
    }
}
