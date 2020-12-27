package com.stoom.demo;

import com.stoom.demo.entities.Game;
import com.stoom.demo.entities.Review;
import com.stoom.demo.entities.User;
import com.stoom.demo.repositories.GameRepository;
import com.stoom.demo.repositories.ReviewRepository;
import com.stoom.demo.repositories.UserRepository;
import com.stoom.demo.requests.ReviewRequest;
import com.stoom.demo.services.ReviewService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewServiceTests {
    @Autowired
    private ReviewService reviewService;

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private GameRepository gameRepository;

    @Test
    public void createReviewTestCorrect(){
        String gameID = UUID.randomUUID().toString();
        String userID = UUID.randomUUID().toString();

        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setReviewAssessment((int) (Math.random()*10));
        reviewRequest.setReviewGameID(gameID);
        reviewRequest.setReviewUserID(userID);
        reviewRequest.setReviewText("YesYesYes");

        when(userRepository.existsById(userID)).thenReturn(true);
        when(gameRepository.existsById(gameID)).thenReturn(true);

        when(userRepository.findById(userID))
                .thenReturn(Optional.of(new User(UUID.randomUUID().toString(), null, "testuser", "0010", "ROLE_CUSTOMER", null)));

        when(gameRepository.findById(gameID))
                .thenReturn(Optional.of(new Game(UUID.randomUUID().toString(), "Why so serious", null, 6969, "some link")));

        assertEquals(201, reviewService.createReview(reviewRequest).getStatusCodeValue());
    }

    @Test
    public void createReviewTestFB(){
        String gameID = UUID.randomUUID().toString();
        String userID = UUID.randomUUID().toString();

        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setReviewAssessment((int) (Math.random()*10));
        reviewRequest.setReviewGameID(gameID);
        reviewRequest.setReviewUserID(userID);
        reviewRequest.setReviewText("");

        when(userRepository.existsById(userID)).thenReturn(true);
        when(gameRepository.existsById(gameID)).thenReturn(true);

        assertEquals(403, reviewService.createReview(reviewRequest).getStatusCodeValue());
    }

    @Test
    public void createReviewTestBR(){
        String gameID = UUID.randomUUID().toString();
        String userID = UUID.randomUUID().toString();

        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setReviewAssessment((int) (Math.random()*10));
        reviewRequest.setReviewGameID(gameID);
        reviewRequest.setReviewUserID(userID);
        reviewRequest.setReviewText("");

        when(userRepository.existsById(userID)).thenReturn(false);
        when(gameRepository.existsById(gameID)).thenReturn(true);

        assertEquals(400, reviewService.createReview(reviewRequest).getStatusCodeValue());
    }

    @Test
    public void getGameReviewsByAssessmentCorrect(){
        float assessment = (float) (Math.random()*10);
        String gameID = UUID.randomUUID().toString();

        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setReviewAssessment((int) (Math.random()*10));
        reviewRequest.setReviewGameID(gameID);

        User user = new User(UUID.randomUUID().toString(), null, "testuser", "0011", "ROLE_CUSTOMER", null);
        Game game = new Game(gameID, "gameTitle", null, 1999, "someURL");

        Review review = new Review(UUID.randomUUID().toString(), "Review Text 1", assessment, game, user);

        when(gameRepository.existsById(reviewRequest.getReviewGameID()))
                .thenReturn(true);

        when(gameRepository.findById(reviewRequest.getReviewGameID()))
                .thenReturn(Optional.of(game));

        when(reviewRepository.findAllByReviewGameAndReviewAssessment(game, assessment))
                .thenReturn(Stream.of(review).collect(Collectors.toList()));

        assertEquals(202, reviewService.getGameReviewsByAssessment(reviewRequest).getStatusCodeValue());
        assertEquals(0, Objects.requireNonNull(reviewService.getGameReviewsByAssessment(reviewRequest).getBody()).size());
    }

    @Test
    public void getGameReviewsByAssessmentBR(){
        float assessment = (float) (Math.random()*10);
        String gameID = UUID.randomUUID().toString();
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setReviewAssessment((int) (Math.random()*10));
        reviewRequest.setReviewGameID(gameID);

       when(gameRepository.existsById(reviewRequest.getReviewGameID())).thenReturn(false);

        assertEquals(400, reviewService.getGameReviewsByAssessment(reviewRequest).getStatusCodeValue());
    }
}
