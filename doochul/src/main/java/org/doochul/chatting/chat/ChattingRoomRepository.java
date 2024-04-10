package org.doochul.chatting.chat;

import java.util.List;
import org.doochul.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {

    List<ChattingRoom> findByUserOrReceiver(User user, String receiver);

    ChattingRoom findByIdAndUserOrIdAndReceiver(Long id, User user, Long id1, String nickname);

    ChattingRoom findBySenderAndReceiver(String nickname, String receiver);

    ChattingRoom findByRoomIdAndUserOrRoomIdAndReceiver(String roomId, User user, String roomId1, String nickname);

    ChattingRoom findByRoomId(String roomId);
}
