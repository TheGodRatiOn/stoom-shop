package com.stoom.demo;

import com.stoom.demo.entities.User;
import com.stoom.demo.repositories.UserRepository;
import com.stoom.demo.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void getUserIDTestCorrect(){
        when(userRepository.findByUserName("TestUser"))
                .thenReturn(new User(UUID.randomUUID().toString(), null, "TestUser", "0011", "ROLE_CUSTOMER", null));
        assertEquals(200, userService.getUserID("TestUser").getStatusCodeValue());
    }

    @Test
    public void getUserIDTestBR(){
        when(userRepository.findByUserName("TestUser"))
                .thenReturn(null);
        assertEquals(400, userService.getUserID("TestUser").getStatusCodeValue());
    }

    @Test
    public void getUsersByUserNameTestBR(){
        String userName = "TestUser";

        when(userRepository.findAllByUserNameContaining(userName)).thenReturn(null);
        assertEquals(400, userService.getUsersByUserName(userName).getStatusCodeValue());
    }

    @Test
    public void getUsersByUserNameTestCorrect(){
        String userName = "TestUser";

        when(userRepository.findAllByUserNameContaining(userName))
                .thenReturn(Stream.of(
                        new User(UUID.randomUUID().toString(), null, "TestTestUser", "0015", "ROLE_CUSTOMER", null),
                        new User(UUID.randomUUID().toString(), null, "TestUser", "0016", "ROLE_CUSTOMER", null))
                .collect(Collectors.toList()));
        assertEquals(202, userService.getUsersByUserName(userName).getStatusCodeValue());
        assertEquals(2, Objects.requireNonNull(userService.getUsersByUserName(userName).getBody()).size());
    }
}
