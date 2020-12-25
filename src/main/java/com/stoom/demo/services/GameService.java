package com.stoom.demo.services;

import com.stoom.demo.repositories.UserRepository;
import com.stoom.demo.requests.GameRequest;
import com.stoom.demo.entities.Game;
import com.stoom.demo.repositories.GameRepository;
import com.stoom.demo.responses.GameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public ResponseEntity<List<GameResponse>> getGameByTitle(String gameTitle){
        if (!gameRepository.findAllByGameTitleContaining(gameTitle).isEmpty()) {
            List<Game> games = gameRepository.findAllByGameTitleContaining(gameTitle);
            List<GameResponse> gameResponses = new ArrayList<>();

            for (Game game : games) {
                gameResponses.add(new GameResponse(game));
            }
            return new ResponseEntity<>(gameResponses, HttpStatus.ACCEPTED);
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
        float multiplier = (1 - (gameRequest.getGameReqPrice()/100.0f));
        String userID = gameRequest.getGameReqURL();

        if ((!gameRepository.findAllByGameTitleContaining(gameRequest.getGameReqTitle()).isEmpty()) && (userRepository.findById(userID).isPresent())){
                List<Game> games = gameRepository.findAllByGameTitleContaining(gameRequest.getGameReqTitle());
                games.forEach(game -> game.setGamePrice(Math.round(game.getGamePrice() * multiplier)));
                gameRepository.saveAll(games);

                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<GameResponse>> getAllGames(){
        List<GameResponse> gameResponses = new ArrayList<>();

        for (Game game: gameRepository.findAll()) {
            gameResponses.add(new GameResponse(game));
        }

        if (gameResponses.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(gameResponses, HttpStatus.ACCEPTED);
        }
    }

    public ResponseEntity<GameResponse> getGame(String gameID){
        UUID gameUUID = UUID.fromString(gameID);

        if (gameRepository.findById(gameUUID.toString()).isPresent()){
            GameResponse gameResponse = new GameResponse(gameRepository.findById(gameUUID.toString()).get());

            return new ResponseEntity<>(gameResponse, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
