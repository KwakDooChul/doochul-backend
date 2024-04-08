package org.doochul.chatting;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.doochul.chatting.dto.ChattingRequest;
import org.doochul.resolver.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChattingRoomController {

    private final ChattingService chattingService;

    @GetMapping("/rooms")
    public ResponseEntity<List<ChattingRoom>> room() {
        return ResponseEntity.ok(chattingService.findAllRooms());
    }

    @PostMapping("/room")
    public ResponseEntity<ChattingRoom> createRoom(
            @AuthenticationPrincipal final Long id,
            @RequestBody ChattingRequest chattingRequest,
            @RequestParam final String name
    ) {
        final String chatRoomId = chattingService.createChatRoom(id, chattingRequest, name).getRoomId();
        return ResponseEntity.created(URI.create("/chat/room/" + chatRoomId)).build();
    }

    @GetMapping("/room/{roomId}")
    public ResponseEntity<ChattingRoom> roomInfo(@PathVariable final String roomId) {
        return ResponseEntity.ok(chattingService.findRoomById(roomId));
    }
}

