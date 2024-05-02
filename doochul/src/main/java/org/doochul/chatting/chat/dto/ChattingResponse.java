package org.doochul.chatting.chat.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.doochul.chatting.chat.ChattingRoom;

@Getter
@Setter
public class ChattingResponse {

    private Long id;
    private String roomName;
    private String sender;
    private String roomId;
    private String receiver;
    private String message;
    private LocalDateTime createdAt;

    public ChattingResponse(ChattingRoom chattingRoom) {
        this.id = chattingRoom.getId();
        this.roomName = chattingRoom.getRoomName();
        this.sender = chattingRoom.getSender();
        this.roomId = chattingRoom.getRoomId();
        this.receiver = chattingRoom.getReceiver();
    }

    public ChattingResponse(Long id, String roomName, String roomId, String sender, String receiver) {
        this.id = id;
        this.roomName = roomName;
        this.roomId = roomId;
        this.sender = sender;
        this.receiver = receiver;
    }

    public ChattingResponse(String roomId) {
        this.roomId = roomId;
    }

    public void setLatestMessageContent(String message) {
        this.message = message;
    }

    public void setLatestMessageCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
