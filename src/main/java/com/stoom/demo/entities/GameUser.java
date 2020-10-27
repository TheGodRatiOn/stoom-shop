package com.stoom.demo.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "game_users")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class GameUser {
    @Id
    @Column(name = "game_fk", nullable = false)
    private String guGameID;

    @Column(name = "user_fk")
    private String guUserID;
}
