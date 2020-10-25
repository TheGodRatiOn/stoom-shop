package com.stoom.demo.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Table(name = "user")
@Entity
@Data
public class User {
    @Id
    @Column(name = "primary_key", nullable = false)
    private String userID;

    @OneToMany(mappedBy = "reviewUser")
    private List<Review> userReviews;

    @Column(name = "column2")
    private int userCol2;

    @Column(name = "role")
    private String userRole;

    @OneToMany(mappedBy = "messageUser")
    private List<Message> userMessages;
}