package com.stoom.demo;

import com.stoom.demo.entities.Game;
import com.stoom.demo.entities.GameUser;
import com.stoom.demo.entities.User;
import com.stoom.demo.repositories.GameRepository;
import com.stoom.demo.repositories.GameUserRepository;
import com.stoom.demo.repositories.UserRepository;
import com.stoom.demo.requests.GameUserRequest;
import com.stoom.demo.responses.GameResponse;
import com.stoom.demo.services.GameUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameUserServiceTests {
    @Autowired
    private GameUserService gameUserService;

    @MockBean
    private GameRepository gameRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private GameUserRepository gameUserRepository;

    @Test
    public void getUserGamesTestCorrect() {
        String userID = UUID.randomUUID().toString();

        User user = new User(userID, null, "user", "0000", "ROLE_EMPLOYEE", null);
        when(userRepository.existsById(user.getUserID()))
                .thenReturn(true);

        String gameID1 = UUID.randomUUID().toString();

        when(gameUserRepository.findAllByGuUserID(user.getUserID()))
                .thenReturn(List.of(new GameUser(UUID.randomUUID().toString(), user.getUserID(), gameID1)));

        when(gameRepository.findById(user.getUserID())).
                thenReturn(Optional.of(new Game(gameID1, "gameTitle", null, 1999, "some kind of link")));

        GameResponse gameResponse = new GameResponse();
        gameResponse.setGameResURL("some kind of link");
        gameResponse.setGameResTitle("gameTitle");
        gameResponse.setGameResPrice(1999);
        gameResponse.setGameResID(gameID1);

        assertTrue(new ReflectionEquals(gameResponse).matches(Objects.requireNonNull(gameUserService.getUserGames(userID).getBody()).get(0)));
    }

    @Test
    public void getUserGamesTestBR() {
        String userID = UUID.randomUUID().toString();

        User user = new User(userID, null, "user", "0000", "ROLE_EMPLOYEE", null);
        when(userRepository.existsById(user.getUserID()))
                .thenReturn(false);

        assertEquals(400, gameUserService.getUserGames(userID).getStatusCodeValue());
    }

    @Test
    public void createUserGameTestForbidden() {
        String gameID = UUID.randomUUID().toString();
        String userID = UUID.randomUUID().toString();

        when(userRepository.existsById(userID)).thenReturn(true);
        when(gameRepository.existsById(gameID)).thenReturn(true);
        when(gameUserRepository.findAllByGuUserID(userID)).thenReturn(List.of(new GameUser(UUID.randomUUID().toString(), gameID, userID)));

        GameUserRequest gameUserRequest = new GameUserRequest();
        gameUserRequest.setReviewGameID(gameID);
        gameUserRequest.setReviewUserID(userID);

        assertEquals(403, gameUserService.createGameUser(gameUserRequest).getStatusCodeValue());
    }

    @Test
    public void createUserGameTestCorrect() {
        String gameID = UUID.randomUUID().toString();
        String userID = UUID.randomUUID().toString();

        when(userRepository.existsById(userID))
                .thenReturn(true);
        when(gameRepository.existsById(gameID))
                .thenReturn(true);
        when(gameUserRepository.findAllByGuUserID(userID))
                .thenReturn(List.of());
        when(gameUserRepository.save(new GameUser(UUID.randomUUID().toString(), gameID, userID)))
                .thenReturn(null);

        GameUserRequest gameUserRequest = new GameUserRequest();
        gameUserRequest.setReviewGameID(gameID);
        gameUserRequest.setReviewUserID(userID);

        assertEquals(201, gameUserService.createGameUser(gameUserRequest).getStatusCodeValue());
    }

    @Test
    public void createUserGameTestBR() {
        String gameID = UUID.randomUUID().toString();
        String userID = UUID.randomUUID().toString();

        when(userRepository.existsById(userID))
                .thenReturn(false);
        when(gameRepository.existsById(gameID))
                .thenReturn(true);
        when(gameUserRepository.findAllByGuUserID(userID))
                .thenReturn(List.of());
        when(gameUserRepository.save(new GameUser(UUID.randomUUID().toString(), gameID, userID)))
                .thenReturn(null);

        GameUserRequest gameUserRequest = new GameUserRequest();
        gameUserRequest.setReviewGameID(gameID);
        gameUserRequest.setReviewUserID(userID);

        assertEquals(400, gameUserService.createGameUser(gameUserRequest).getStatusCodeValue());
    }
}
