package com.stoom.demo.controllers;

import com.stoom.demo.entities.Message;
import com.stoom.demo.requests.MessageRequest;
import com.stoom.demo.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
@Validated
public class MessageController {
    private MessageService messageService;

    @PostMapping("/")
    public ResponseEntity<HttpStatus> createMessage(@Valid @RequestBody MessageRequest messageRequest){
        return messageService.createMessage(messageRequest);
    }

    @GetMapping("/{userID}/{role}")
    public ResponseEntity<List<Message>> getAllEmployeeMessages(@Valid @PathVariable(name = "userID") String userID,
                                                                @Valid @PathVariable(name = "roleID") String role){
        return messageService.getAllUserMessages(userID, role);
    }
}
