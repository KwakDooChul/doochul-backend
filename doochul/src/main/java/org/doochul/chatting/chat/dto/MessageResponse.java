package org.doochul.chatting.chat.dto;

public record MessageResponse(
        String message,
        int statusValue
) {
}
