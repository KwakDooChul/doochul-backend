package org.doochul.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.doochul.domain.Role;
import org.doochul.domain.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSaveRequestDto {

    private String username;
    private String password;
    private String gender;
    private Role role;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .gender(gender)
                .role(Role.STUDENT)
                .build();
    }
}
