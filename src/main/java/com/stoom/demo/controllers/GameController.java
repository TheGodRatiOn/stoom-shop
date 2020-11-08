package com.stoom.demo.controllers;


import com.stoom.demo.requests.GameRequest;
import com.stoom.demo.responses.GameResponse;
import com.stoom.demo.services.GameService;
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
@RequestMapping("/stoom/game")
@Validated
@Api(value = "GameController")
public class GameController {
    private final GameService gameService;

    @GetMapping("/{title}")
    public ResponseEntity<List<GameResponse>> getGameByTitle(@Valid @PathVariable(name = "title") String gameTitle){
        return gameService.getGameByTitle(gameTitle);
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> createGame(@Valid @RequestBody GameRequest gameRequest){
        return gameService.createGameByRequest(gameRequest);
    }

    @PutMapping("/")
    public ResponseEntity<HttpStatus> startTitleSale(@Valid @RequestBody GameRequest gameRequest){
        return gameService.startTitleSale(gameRequest);
    }
}
