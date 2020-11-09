package com.stoom.demo.controllers;

import com.stoom.demo.requests.GameUserRequest;
import com.stoom.demo.responses.GameResponse;
import com.stoom.demo.services.GameUserService;
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
@RequestMapping("/stoom/game_user")
@Validated
@Api(value = "GameUserController")
public class GameUserController {
    private final GameUserService gameUserService;

    @PostMapping("/")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> createGameUser(@Valid @RequestBody GameUserRequest gameUserRequest){
        return gameUserService.createGameUser(gameUserRequest);
    }

    @GetMapping("/")
    @PreAuthorize(value = "hasRole('ROLE_CUSTOMER') or hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<GameResponse>> getUserGames(@Valid @RequestParam String userID){
        return gameUserService.getUserGames(userID);
    }
}