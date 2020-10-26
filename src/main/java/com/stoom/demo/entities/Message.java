package com.stoom.demo.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "message")
@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Message {
    @Id
    @Column(name = "primary_key", nullable = false)
    private String messageID;

    @ManyToOne
    @JoinColumn(name = "user_sender_fk", nullable = false)
    private User messageSenderUser;

    @Column(name = "user_reciever_fk", nullable = false)
    private String messageReceiverUser;

    @Column(name = "text")
    private String messageText;

    @Column(name = "date_time")
    private LocalDateTime messageDate;
}
