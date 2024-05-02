package org.doochul.chatting.chat.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.doochul.domain.user.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChattingRoomResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 6494678977089006639L;

    private Long id;
    private String roomName;
    private String roomId;
    private String sender;
    private String receiver;

    public static ChattingRoomResponse create(User user, User teacher) {
        ChattingRoomResponse chattingRoomResponse = new ChattingRoomResponse();
        chattingRoomResponse.roomName = teacher.getName();
        chattingRoomResponse.roomId = UUID.randomUUID().toString();
        chattingRoomResponse.sender = user.getName();
        chattingRoomResponse.receiver = teacher.getName();
        return chattingRoomResponse;
    }
}
