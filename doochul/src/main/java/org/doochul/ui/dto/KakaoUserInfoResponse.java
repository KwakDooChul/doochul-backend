package org.doochul.ui.dto;

import lombok.Getter;

@Getter
public class KakaoUserInfoResponse {
    private Long id;
    private String connected_at;
    private KakaoAccount kakao_account;
}
