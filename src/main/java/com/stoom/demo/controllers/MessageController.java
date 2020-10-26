package com.stoom.demo.controllers;

import com.stoom.demo.requests.MessageRequest;
import com.stoom.demo.responses.MessageResponse;
import com.stoom.demo.services.MessageService;
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
@RequestMapping("/message")
@Validated
@Api(value = "MessageController")
public class MessageController {
    private MessageService messageService;

    @PostMapping("/")
    public ResponseEntity<HttpStatus> createMessage(@Valid @RequestBody MessageRequest messageRequest){
        return messageService.createMessage(messageRequest);
    }

    @GetMapping("/{userID}/{role}")
    public ResponseEntity<List<MessageResponse>> getAllEmployeeMessages(@Valid @PathVariable(name = "userID") String userID,
                                                                        @Valid @PathVariable(name = "roleID") String role){
        return messageService.getAllUserMessages(userID, role);
    }
}
