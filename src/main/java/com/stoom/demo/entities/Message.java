package com.stoom.demo.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "message")
@Entity
@Data
public class Message {
    @Id
    @Column(name = "primary_key", nullable = false)
    private String messageID;

    @ManyToOne
    @JoinColumn(name = "user_fk", nullable = false)
    private User messageUser;

    @Column(name = "text")
    private String messageText;

    @Column(name = "date_time")
    private LocalDate messageDate;
}
