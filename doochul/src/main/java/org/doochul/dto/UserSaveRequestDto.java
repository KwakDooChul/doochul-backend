package org.doochul.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.doochul.domain.user.Gender;
import org.doochul.domain.user.Identity;
import org.doochul.domain.user.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSaveRequestDto {

    private String username;
    private String password;
    private Gender gender;
    private Identity identity;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .gender(gender)
                .identity(Identity.GENERAL)
                .build();
    }
}
