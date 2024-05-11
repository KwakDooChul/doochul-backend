package org.doochul.ui.dto;

public record LoginRequest (
        String socialType,
        String authorizationCode
) {
}
