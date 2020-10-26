package com.stoom.demo.controllers;

import com.stoom.demo.entities.Review;
import com.stoom.demo.requests.ReviewRequest;
import com.stoom.demo.services.ReviewService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Validated
@Api(value = "ReviewController")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{assessment}")
    public ResponseEntity<List<Review>> getReviewsByAssessment(@Valid @PathVariable(name = "assessment") String reviewAssessment){
        return reviewService.getReviewByAssessment(reviewAssessment);
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody ReviewRequest reviewRequest){
        return reviewService.createReview(reviewRequest);
    }
}