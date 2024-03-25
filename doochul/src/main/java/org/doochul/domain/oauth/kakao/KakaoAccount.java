package org.doochul.domain.oauth.kakao;

import lombok.Data;

@Data
public class KakaoAccount {
    private Profile profile;
    private Boolean has_email;
    private Boolean email_needs_agreement;
    private Boolean is_email_valid;
    private Boolean is_email_verified;
    private String email;
}
