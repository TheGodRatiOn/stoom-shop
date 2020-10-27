package com.stoom.demo.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "messages")
@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Message {
    @Id
    @Column(name = "id", nullable = false)
    private String messageID;

    @ManyToOne
    @JoinColumn(name = "user_sender_fk", nullable = false)
    private User messageSenderUser;

    @Column(name = "user_reciever_fk", nullable = false)
    private String messageReceiverUser;

    @Column(name = "text_m")
    private String messageText;

    @Column(name = "time_date")
    private LocalDateTime messageDate;
}
