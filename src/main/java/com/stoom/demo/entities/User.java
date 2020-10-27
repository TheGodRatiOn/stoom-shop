package com.stoom.demo.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table(name = "users")
@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private String userID;

    @OneToMany(mappedBy = "reviewUser")
    private List<Review> userReviews;

    @Column(name = "name", nullable = false)
    private String userName;

    @Column(name = "role", nullable = false)
    private String userRole;

    @OneToMany(mappedBy = "messageSenderUser")
    private List<Message> userMessages;
}