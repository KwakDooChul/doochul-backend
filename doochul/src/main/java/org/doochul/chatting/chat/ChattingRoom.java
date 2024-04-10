package org.doochul.chatting.chat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.doochul.domain.user.User;

@Entity
@Setter
@Getter
@Table(name = "messageRoom")
@NoArgsConstructor
public class ChattingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;

    private String sender;

    @Column(unique = true)
    private String roomId;

    private String receiver;

    @OneToMany(mappedBy = "chattingRoom", cascade = CascadeType.REMOVE)
    private List<Chatting> chattingList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;


    public ChattingRoom(Long id, String roomName, String sender, String roomId, String receiver, User user) {
        this.id = id;
        this.roomName = roomName;
        this.sender = sender;
        this.roomId = roomId;
        this.receiver = receiver;
        this.user = user;
    }
}
