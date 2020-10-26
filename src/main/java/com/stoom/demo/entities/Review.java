package com.stoom.demo.entities;

import lombok.*;

import javax.persistence.*;

@Table(name = "review")
@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Review {
    @Id
    @Column(name = "primary_key", nullable = false)
    private String reviewPK;

    @Column(name = "text")
    private String reviewText;

    @Column(name = "assessment", nullable = false)
    private float reviewAssessment;

    @ManyToOne
    @JoinColumn(name = "game_fk", nullable = false)
    private Game reviewGame;

    @ManyToOne
    @JoinColumn(name = "user_fk", nullable = false)
    private User reviewUser;
}
