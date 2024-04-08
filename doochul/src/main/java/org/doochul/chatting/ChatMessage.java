package org.doochul.chatting;

import lombok.Getter;
import lombok.Setter;
import org.doochul.domain.user.User;

@Getter
@Setter
public class ChatMessage {

    private MessageType type;
    private String roomId;
    private User sender;
    private String message;
}
