package com.stoom.demo.controllers;

import com.stoom.demo.requests.GameUserRequest;
import com.stoom.demo.responses.GameResponse;
import com.stoom.demo.services.GameUserService;
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
@RequestMapping("/stoom/game_user")
@Validated
@Api(value = "GameUserController")
public class GameUserController {
    private GameUserService gameUserService;

    @PostMapping("/")
    public ResponseEntity<HttpStatus> createGameUser(@Valid @RequestBody GameUserRequest gameUserRequest){
        return gameUserService.createGameUser(gameUserRequest);
    }

    @GetMapping("/{userID}")
    public ResponseEntity<List<GameResponse>> getUserGames(@Valid @PathVariable(name = "userID") String userID){
        return gameUserService.getUserGames(userID);
    }
}