package com.stoom.demo.controllers;

import com.stoom.demo.requests.ReviewRequest;
import com.stoom.demo.responses.ReviewResponse;
import com.stoom.demo.services.ReviewService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stoom/review")
@Validated
@Api(value = "ReviewController")
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/{assessment}")
    @PreAuthorize(value = "hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_USER')")
    public ResponseEntity<List<ReviewResponse>> getReviewsByAssessment(@Valid @PathVariable(name = "assessment") String reviewAssessment){
        return reviewService.getReviewByAssessment(reviewAssessment);
    }

    @PostMapping("/")
    @PreAuthorize(value = "hasRole('ROLE_CUSTOMER') or hasRole('ROLE_ADMIN')or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<HttpStatus> createReview(@Valid @RequestBody ReviewRequest reviewRequest){
        return reviewService.createReview(reviewRequest);
    }
}