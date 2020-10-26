package com.stoom.demo.services;

import com.stoom.demo.requests.GameRequest;
import com.stoom.demo.entities.Game;
import com.stoom.demo.repositories.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public ResponseEntity<List<Game>> getGameByTitle(String gameTitle){
        if (!gameRepository.findAllByGameTitleContaining(gameTitle).isEmpty()) {
            return new ResponseEntity<>(gameRepository.findAllByGameTitleContaining(gameTitle), HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<HttpStatus> createGameByRequest(GameRequest gameRequest){
        try {
            Game game = new Game(UUID.randomUUID().toString(), gameRequest.getGameReqTitle(), null, gameRequest.getGameReqPrice(), gameRequest.getGameReqURL());
            gameRepository.save(game);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
