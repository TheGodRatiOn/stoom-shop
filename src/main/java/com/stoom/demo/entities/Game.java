package com.stoom.demo.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "game")
public class Game {
    @Id
    @Column(name = "primary_key", nullable = false)
    private String gameID;

    @Column(name = "name", nullable = false)
    private String gameTitle;

    @OneToMany(mappedBy = "reviewGame")
    private List<Review> gameReviews;

    @Column(name = "price", nullable = false)
    private int gamePrice;

    @Column(name = "url")
    private String gameURL;
}