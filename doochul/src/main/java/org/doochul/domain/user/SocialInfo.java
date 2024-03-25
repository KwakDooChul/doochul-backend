package org.doochul.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class SocialInfo {

    @Column(length = 100, nullable = false, unique = true)
    private String keyCode; // 로그인 식별키

    @Column
    private String nickname;

    @Builder
    public SocialInfo(String keyCode, String nickname) {
        this.keyCode = keyCode;
        this.nickname = nickname;
    }
}
