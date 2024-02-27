package org.doochul.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length =30)
    private String userName;

    @Column(nullable = false, length =100)
    private String password;

    @Column(nullable = false, length =10)
    private String gender;


    private String memberShip; // MemberShip으로 나중에 수정

    private String study; // Study로 나중에 수정

    @ColumnDefault("'user'")
    private String role;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

}
