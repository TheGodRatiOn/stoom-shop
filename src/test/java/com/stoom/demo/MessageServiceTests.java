package com.stoom.demo;

import com.stoom.demo.entities.Message;
import com.stoom.demo.entities.User;
import com.stoom.demo.repositories.MessageRepository;
import com.stoom.demo.repositories.UserRepository;
import com.stoom.demo.requests.MessageRequest;
import com.stoom.demo.services.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTests {

    @Autowired
    private MessageService messageService;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void createMessageTestBR(){
        String userID1 = UUID.randomUUID().toString();
        String userID2 = UUID.randomUUID().toString();

        when(userRepository.existsById(userID1)).thenReturn(true);
        when(userRepository.existsById(userID2)).thenReturn(false);

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setMessageText("TestText");
        messageRequest.setMessageSenderUserID(userID1);
        messageRequest.setMessageRecieverUserID(userID2);

        assertEquals(403, messageService.createMessage(messageRequest).getStatusCodeValue());
    }

    @Test
    public void createMessageTestCorrect(){
        String userID1 = UUID.randomUUID().toString();
        String userID2 = UUID.randomUUID().toString();

        when(userRepository.existsById(userID1)).thenReturn(true);
        when(userRepository.existsById(userID2)).thenReturn(true);

        MessageRequest messageRequest = new MessageRequest();
        messageRequest.setMessageText("TestText");
        messageRequest.setMessageSenderUserID(userID1);
        messageRequest.setMessageRecieverUserID(userID2);

        when(userRepository.findById(userID1)).thenReturn(Optional.of(new User(userID2,null, "", "", "ROLE_CUSTOMER", null)));

        assertEquals(201, messageService.createMessage(messageRequest).getStatusCodeValue());
    }

    @Test
    public void getAllUserMessagesTestCorrect(){
        String userID1 = UUID.randomUUID().toString();
        String userID2 = UUID.randomUUID().toString();
        when(userRepository.existsById(userID1)).thenReturn(true);

        User user1 = new User(userID1, null, "", "0007", "ROLE_CUSTOMER", null);
        User user2 = new User(userID2, null, "", "0008", "ROLE_CUSTOMER", null);

        when(userRepository.findById(userID1)).thenReturn(Optional.of(user1));

        when(messageRepository.findAllByMessageSenderUser(user1))
                .thenReturn(Stream.of(
                        new Message(UUID.randomUUID().toString(), user1, userID2, "Message Text 1", LocalDateTime.now()),
                        new Message(UUID.randomUUID().toString(), user1, userID2, "Message Text 2", LocalDateTime.now()))
                .collect(Collectors.toList()));

        when(messageRepository.findAllByMessageReceiverUser(userID1))
                .thenReturn(Stream.of(
                        new Message(UUID.randomUUID().toString(), user2, userID1, "Message Text 3", LocalDateTime.now()))
                .collect(Collectors.toList()));

        assertEquals(3, Objects.requireNonNull(messageService.getAllUserMessages(userID1).getBody()).size());
        assertEquals(202, messageService.getAllUserMessages(userID1).getStatusCodeValue());
    }

    @Test
    public void getAllUserMessagesTestBR() {
        String userID1 = UUID.randomUUID().toString();
        when(userRepository.existsById(userID1)).thenReturn(false);

        assertEquals(400, messageService.getAllUserMessages(userID1).getStatusCodeValue());
    }
}
