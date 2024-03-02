package org.doochul.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.doochul.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String deviceToken;

    private String passWord;

    private Gender gender;

    private Identity identity;

    public User(
            final Long id,
            final String name,
            final String deviceToken,
            final String passWord,
            final Gender gender,
            final Identity identity
    ) {
        this.id = id;
        this.name = name;
        this.deviceToken = deviceToken;
        this.passWord = passWord;
        this.gender = gender;
        this.identity = identity;
    }
}
