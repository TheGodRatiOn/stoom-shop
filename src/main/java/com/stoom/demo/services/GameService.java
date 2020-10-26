package com.stoom.demo.services;

import com.stoom.demo.repositories.UserRepository;
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
    private final UserRepository userRepository;

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

    public ResponseEntity<HttpStatus> startTitleSale(GameRequest gameRequest){
        float multiplier = (1 - (gameRequest.getGameReqPrice()/100));
        String userID = gameRequest.getGameReqURL();

        if ((!gameRepository.findAllByGameTitleContaining(gameRequest.getGameReqTitle()).isEmpty()) && (userRepository.findById(userID).isPresent())){
            if (userRepository.findById(userID).get().getUserRole().equals("EMPLOYEE")){
                List<Game> games = gameRepository.findAllByGameTitleContaining(gameRequest.getGameReqTitle());
                games.stream().forEach(game -> game.setGamePrice(Math.round(game.getGamePrice() * multiplier)));
                gameRepository.saveAll(games);

                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
