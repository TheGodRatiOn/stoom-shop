package com.stoom.demo.repositories;

import com.stoom.demo.entities.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, String> {
    List<Game> findAllByGameTitleContaining(String gameTitle);
}
