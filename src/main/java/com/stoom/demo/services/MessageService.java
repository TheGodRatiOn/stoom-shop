package com.stoom.demo.services;

import com.stoom.demo.entities.Message;
import com.stoom.demo.enums.Role;
import com.stoom.demo.repositories.MessageRepository;
import com.stoom.demo.repositories.UserRepository;
import com.stoom.demo.requests.MessageRequest;
import com.stoom.demo.responses.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public ResponseEntity<HttpStatus> createMessage(MessageRequest messageRequest) {
        if (userRepository.existsById(messageRequest.getMessageSenderUserID())) {
            LocalDateTime localDateTime = LocalDateTime.now();
            Random random = new Random();

            int empListSize = userRepository.findByUserRole(Role.ROLE_EMPLOYEE.name()).size();

            if (empListSize > 0) {
                Message message = new Message(UUID.randomUUID().toString(),
                        userRepository.findById(messageRequest.getMessageSenderUserID()).get(),
                        userRepository.findByUserRole(Role.ROLE_EMPLOYEE.name()).get(random.nextInt(empListSize)).getUserID(),
                        messageRequest.getMessageText(),
                        localDateTime);
                messageRepository.save(message);
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    public ResponseEntity<HttpStatus> respondToCustomer(MessageRequest messageRequest){
        if (userRepository.existsById(messageRequest.getMessageSenderUserID()) && userRepository.existsById(messageRequest.getMessageRecieverUserID())){
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

    public ResponseEntity<List<MessageResponse>> getAllUserMessages(String userID){
        if (userRepository.existsById(userID)){
            List<Message> messages = messageRepository.findAllByMessageSenderUser(userRepository.findById(userID).get());
            messages.addAll(messageRepository.findAllByMessageReceiverUser(userID));

            List<MessageResponse> messageResponses = new ArrayList<>();
            for (Message message : messages) {
                messageResponses.add(new MessageResponse(message));
            }
            return new ResponseEntity<>(messageResponses, HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}