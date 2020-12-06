package com.stoom.demo.services;

import com.stoom.demo.entities.Game;
import com.stoom.demo.entities.GameUser;
import com.stoom.demo.repositories.GameRepository;
import com.stoom.demo.repositories.GameUserRepository;
import com.stoom.demo.repositories.UserRepository;
import com.stoom.demo.requests.GameUserRequest;
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
public class GameUserService {
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final GameUserRepository gameUserRepository;

    public ResponseEntity<HttpStatus> createGameUser(GameUserRequest gameUserRequest){
        if ((userRepository.findById(gameUserRequest.getReviewGameID()).isPresent() && gameRepository.findById(gameUserRequest.getReviewGameID()).isPresent())){
            GameUser gameUser = new GameUser(UUID.randomUUID().toString(), gameUserRequest.getReviewGameID(), gameUserRequest.getReviewUserID());
            gameUserRepository.save(gameUser);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<GameResponse>> getUserGames(String userID){
        if (userRepository.existsById(userID) && !gameUserRepository.findAllByGuUserID(userID).isEmpty()) {
            List<GameUser> gameUsers = gameUserRepository.findAllByGuUserID(userID);
            List<Game> games = new ArrayList<>();
            for (int i = 0; i < gameUsers.size(); i++) {
                if (gameRepository.findById(gameUsers.get(i).getGuGameID()).isPresent()){
                    games.add(gameRepository.findById(gameUsers.get(i).getGuGameID()).get());
                }
            }

            List<GameResponse> gameResponses = new ArrayList<>();
            for (Game game : games) {
                gameResponses.add(new GameResponse(game));
            }
            return new ResponseEntity<>(gameResponses, HttpStatus.OK);

        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
