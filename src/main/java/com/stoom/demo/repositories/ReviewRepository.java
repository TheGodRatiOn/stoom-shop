package com.stoom.demo.repositories;

import com.stoom.demo.entities.Game;
import com.stoom.demo.entities.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, String> {
    List<Review> findAllByReviewGameAndReviewAssessment(Game game, float assessment);
}
