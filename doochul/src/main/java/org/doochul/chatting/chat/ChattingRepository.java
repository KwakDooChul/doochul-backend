package org.doochul.chatting.chat;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRepository extends JpaRepository<Chatting, Long> {

    List<Chatting> findTop100ByRoomIdOrderByCreatedAtAsc(String roomId);

    Chatting findTopByRoomIdOrderByCreatedAtDesc(String roomId);
}
