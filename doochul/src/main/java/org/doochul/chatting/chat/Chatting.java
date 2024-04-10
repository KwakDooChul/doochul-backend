package org.doochul.chatting.chat;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doochul.domain.BaseEntity;

@Entity
@Getter
@Table(name = "message")
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Chatting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender")
    private String sender;

    @Column(name = "roomId")
    private String roomId;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "message")
    private String message;

    @Column(name = "sentTime")
    private String sentTime;

    @ManyToOne
    @JoinColumn(name = "roomId", referencedColumnName = "roomId", insertable = false, updatable = false)
    private ChattingRoom chattingRoom;

    public Chatting(String sender, String roomId, String message) {
        super();
        this.sender = sender;
        this.roomId = roomId;
        this.message = message;
    }
}    
