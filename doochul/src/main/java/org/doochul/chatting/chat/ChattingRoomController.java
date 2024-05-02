package org.doochul.chatting.chat;

import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.doochul.chatting.chat.dto.ChattingRequest;
import org.doochul.chatting.chat.dto.ChattingResponse;
import org.doochul.chatting.chat.dto.ChattingRoomResponse;
import org.doochul.chatting.chat.dto.MessageResponse;
import org.doochul.resolver.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChattingRoomController {

    private final ChattingRoomService chattingRoomService;

    @PostMapping("/room")
    public ResponseEntity<ChattingResponse> createRoom(
            @RequestBody final ChattingRequest chattingRequest,
            @AuthenticationPrincipal final Long id
    ) {
        final ChattingResponse response = chattingRoomService.createRoom(chattingRequest, id);
        return ResponseEntity.created(URI.create("/chat/room/" + response)).build();
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ChattingResponse>> findAllRoomByUser(@AuthenticationPrincipal final Long id) {
        return ResponseEntity.ok(chattingRoomService.findAllRoomByUser(id));
    }

    @GetMapping("room/{roomId}")
    public ResponseEntity<ChattingRoomResponse> findRoom(
            @PathVariable final String roomId,
            @AuthenticationPrincipal final Long id
    ) {
        return ResponseEntity.ok(chattingRoomService.findRoom(roomId, id));
    }

    @DeleteMapping("room/{roomId}")
    public ResponseEntity<MessageResponse> deleteRoom(
            @PathVariable final Long roomId,
            @AuthenticationPrincipal final Long id
    ) {
        return ResponseEntity.ok(chattingRoomService.deleteRoom(roomId, id));
    }
}

