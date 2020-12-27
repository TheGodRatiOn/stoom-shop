package com.stoom.demo.services;


import com.stoom.demo.entities.Review;
import com.stoom.demo.repositories.GameRepository;
import com.stoom.demo.repositories.ReviewRepository;
import com.stoom.demo.repositories.UserRepository;
import com.stoom.demo.requests.ReviewRequest;
import com.stoom.demo.responses.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;

    public ResponseEntity<List<ReviewResponse>> getGameReviewsByAssessment(ReviewRequest reviewRequest){
        if (gameRepository.existsById(reviewRequest.getReviewGameID())){
            List<Review> reviews = reviewRepository.findAllByReviewGameAndReviewAssessment(gameRepository.findById(reviewRequest.getReviewGameID()).get(), reviewRequest.getReviewAssessment());

            List<ReviewResponse> reviewResponses = new ArrayList<>();
            for (Review review : reviews) {
                reviewResponses.add(new ReviewResponse(review));
            }
            return new ResponseEntity<>(reviewResponses, HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<HttpStatus> createReview(ReviewRequest reviewRequest){
        if ((userRepository.existsById(reviewRequest.getReviewUserID()) && gameRepository.existsById(reviewRequest.getReviewGameID()))){
            if (!reviewRequest.getReviewText().equals("")){
                Review review = new Review(UUID.randomUUID().toString(),
                    reviewRequest.getReviewText(),
                    reviewRequest.getReviewAssessment(),
                    gameRepository.findById(reviewRequest.getReviewGameID()).get(),
                    userRepository.findById(reviewRequest.getReviewUserID()).get());
                reviewRepository.save(review);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
