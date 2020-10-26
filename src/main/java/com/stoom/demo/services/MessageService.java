package com.stoom.demo.services;

import com.stoom.demo.entities.Message;
import com.stoom.demo.repositories.MessageRepository;
import com.stoom.demo.repositories.UserRepository;
import com.stoom.demo.requests.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    private MessageRepository messageRepository;
    private UserRepository userRepository;

    public ResponseEntity<HttpStatus> createMessage(MessageRequest messageRequest) {
        if (userRepository.existsById(messageRequest.getMessageSenderUserID()) && userRepository.existsById(messageRequest.getMessageRecieverUserID())) {
            LocalDateTime localDateTime = LocalDateTime.now();
            Message message = new Message(UUID.randomUUID().toString(),
                    userRepository.findById(messageRequest.getMessageSenderUserID()).get(),
                    messageRequest.getMessageRecieverUserID(),
                    messageRequest.getMessageText(),
                    localDateTime);
            messageRepository.save(message);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<List<Message>> getAllUserMessages(String userID, String role){
        if (userRepository.existsById(userID) && userRepository.findById(userID).get().getUserRole().equals(role)){
            List<Message> messages = messageRepository.findAllByMessageSenderUser(userRepository.findById(userID).get());
            messages.addAll(messageRepository.findAllByMessageReceiverUser(userID));

            return new ResponseEntity<>(messages, HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}