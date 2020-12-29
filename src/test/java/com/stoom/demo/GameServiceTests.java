package com.stoom.demo;

import com.stoom.demo.entities.Game;
import com.stoom.demo.repositories.GameRepository;
import com.stoom.demo.requests.GameRequest;
import com.stoom.demo.responses.GameResponse;
import com.stoom.demo.services.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTests {

    @Autowired
    private GameService gameService;

    @MockBean
    private GameRepository gameRepository;

    @Test
    public void getGameByTitleCorrect(){
        when(gameRepository.findAllByGameTitleContains("Call Of Duty"))
                .thenReturn(Stream.of(
                        new Game(UUID.randomUUID().toString(), "Call Of Duty Modern Warfare", null, 2999, "http://www.example.net/beef/battle.aspx"),
                        new Game(UUID.randomUUID().toString(), "Call Of Duty Cold War", null, 3999, "http://bath.example.com/?believe=aftermath&blood=act"),
                        new Game(UUID.randomUUID().toString(), "Call Of Duty Black Ops 2", null, 1599, "http://art.example.com/?airport=airplane&achiever=boot"),
                        new Game(UUID.randomUUID().toString(), "Call Of Duty Black Ops 3", null, 1999, "https://www.example.com/ants.htm?bird=birds"))
                        .collect(Collectors.toList()));
        assertEquals(4, Objects.requireNonNull(gameService.getGameByTitle("Call Of Duty").getBody()).size());
        assertTrue(Objects.requireNonNull(gameService.getGameByTitle("Call Of Duty").getBody()).stream().allMatch(gameResponse -> gameResponse.getGameResTitle().contains("Call Of Duty")));
        assertEquals(202, gameService.getGameByTitle("Call Of Duty").getStatusCodeValue());
    }

    @Test
    public void getGameTestCorrect(){
        String id = UUID.randomUUID().toString();
        Game game = new Game(id, "The Witcher 3 Wild Hunt", null, 1199, "https://example.org/brick.htm#bottle");
        GameResponse gameResponse = new GameResponse(game);
        when(gameRepository.findById(id))
                .thenReturn(java.util.Optional.of(new Game(id, "The Witcher 3 Wild Hunt", null, 1199, "https://example.org/brick.htm#bottle")));
        assertTrue(new ReflectionEquals(gameResponse).matches(gameService.getGame(id).getBody()));
        assertEquals(200, gameService.getGame(id).getStatusCodeValue());
    }

    @Test
    public void getGameTestBR(){
        String id = UUID.randomUUID().toString();
        when(gameRepository.findById(id))
                .thenReturn(java.util.Optional.empty());
        assertEquals(400, gameService.getGame(id).getStatusCodeValue());
    }

    @Test
    public void getAllGamesTestCorrect(){
        when(gameRepository.findAll())
                .thenReturn(Stream.of(
                        new Game(UUID.randomUUID().toString(), "Call Of Duty Cold War", null, 3999, "http://bath.example.com/?believe=aftermath&blood=act"),
                        new Game(UUID.randomUUID().toString(), "The Crew 2", null, 2999, "https://belief.example.com/books?addition=bed&argument=arm"),
                        new Game(UUID.randomUUID().toString(), "Forza Horizon 4", null, 4999, "http://www.example.com/blood/ball.php"),
                        new Game(UUID.randomUUID().toString(), "Need For Speed Underground 3", null, 3999, "https://www.example.net/badge/bed.php"))
                .collect(Collectors.toList()));
        assertEquals(4, Objects.requireNonNull(gameService.getAllGames().getBody()).size());
        assertEquals(202, gameService.getAllGames().getStatusCodeValue());
    }

    @Test
    public void createGameByRequestTest(){
        GameRequest gameRequest = new GameRequest();
        gameRequest.setGameReqPrice((int) (Math.random() * 20000));
        gameRequest.setGameReqTitle("StringString");
        gameRequest.setGameReqURL("http://www.example.edu/believe?ball=arm&army=brother");
        String uuid = UUID.randomUUID().toString();

        when(gameRepository.save(new Game(uuid, gameRequest.getGameReqTitle(), null, gameRequest.getGameReqPrice(), gameRequest.getGameReqURL())))
                .thenReturn(new Game(uuid, gameRequest.getGameReqTitle(), null, gameRequest.getGameReqPrice(), gameRequest.getGameReqURL()));
        assertEquals(201, gameService.createGameByRequest(gameRequest).getStatusCode().value());
    }

    @Test
    public void startTitleSaleTestBad(){
        GameRequest gameRequest = new GameRequest();
        gameRequest.setGameReqPrice((int) (Math.random() * 100));
        gameRequest.setGameReqTitle("StringString");
        gameRequest.setGameReqURL("");

        when(gameRepository.save(new Game(UUID.randomUUID().toString(), gameRequest.getGameReqTitle(), null, gameRequest.getGameReqPrice(), gameRequest.getGameReqURL())))
                .thenReturn(new Game(UUID.randomUUID().toString(), gameRequest.getGameReqTitle(), null, gameRequest.getGameReqPrice(), gameRequest.getGameReqURL()));
        assertEquals(400, gameService.startTitleSale(gameRequest).getStatusCode().value());
    }
}