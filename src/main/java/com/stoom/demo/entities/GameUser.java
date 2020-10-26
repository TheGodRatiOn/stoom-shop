package com.stoom.demo.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "game_user")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class GameUser {
    @Id
    @Column(name = "primary_key", nullable = false)
    private String guGameID;

    @Column(name = "column")
    private String guUserID;
}
