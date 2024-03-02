package org.doochul.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.doochul.domain.BaseEntity;


@Builder
@Getter
@Setter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length =30)
    private String username;

    private String deviceToken;

    @Column(nullable = false, length =100)
    private String password;

    private Gender gender;

    private Identity identity;

    public User(
            final Long id,
            final String username,
            final String deviceToken,
            final String password,
            final Gender gender,
            final Identity identity
    ) {
        this.id = id;
        this.username = username;
        this.deviceToken = deviceToken;
        this.password = password;
        this.gender = gender;
        this.identity = identity;
    }
}
