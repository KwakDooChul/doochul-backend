package org.doochul.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doochul.domain.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Long socialId;

    private String socialType;

    private String deviceToken;

    private String profileImgUrl;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Identity identity;

    private User(final Identity identity, final Long socialId, final String socialType, final String name) {
        this.identity = identity;
        this.socialId = socialId;
        this.socialType = socialType;
        this.name = name;
    }

    public static User of(final Long socialId, String socialType, final String name) {
        return new User(Identity.GENERAL, socialId, socialType, name);
    }
}
