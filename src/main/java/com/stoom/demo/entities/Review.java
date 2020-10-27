package com.stoom.demo.entities;

import lombok.*;

import javax.persistence.*;

@Table(name = "reviews")
@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Review {
    @Id
    @Column(name = "id", nullable = false)
    private String reviewPK;

    @Column(name = "text_review")
    private String reviewText;

    @Column(name = "asessment", nullable = false)
    private float reviewAssessment;

    @ManyToOne
    @JoinColumn(name = "game_fk", nullable = false)
    private Game reviewGame;

    @ManyToOne
    @JoinColumn(name = "user_fk", nullable = false)
    private User reviewUser;
}
