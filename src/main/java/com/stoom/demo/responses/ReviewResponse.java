package com.stoom.demo.responses;

import com.stoom.demo.entities.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewResponse {
    private String reviewResPK;
    private String reviewResText;
    private float reviewResAssessment;
    private String reviewResGame;
    private String reviewResUser;

    public ReviewResponse(Review review){
        this.setReviewResPK(review.getReviewPK());
        this.setReviewResAssessment(review.getReviewAssessment());
        this.setReviewResText(review.getReviewText());
        this.setReviewResGame(review.getReviewGame().getGameID());
        this.setReviewResUser(review.getReviewUser().getUserID());
    }
}
