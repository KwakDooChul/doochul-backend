package org.doochul.chatting;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.doochul.domain.user.User;

@Getter
@Setter
public class ChattingRoom implements Serializable {

    @Serial
    private static final long serialVersionUID = 6494678977089006639L;

    private String roomId;
    private String name;
    private User userId;
    private User targetId;

    public static ChattingRoom create(final User userId, final User targetId, final String name) {
        ChattingRoom chattingRoom = new ChattingRoom();
        chattingRoom.roomId = UUID.randomUUID().toString();
        chattingRoom.name = name;
        chattingRoom.userId = userId;
        chattingRoom.targetId = targetId;
        return chattingRoom;
    }
}
