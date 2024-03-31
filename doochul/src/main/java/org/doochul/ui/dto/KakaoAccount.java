package org.doochul.ui.dto;

public record KakaoAccount(
        Profile profile
) {
    public record Profile(
            String nickname
    ){
    }
}
