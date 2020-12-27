package com.stoom.demo.controllers;

import com.stoom.demo.requests.MessageRequest;
import com.stoom.demo.responses.MessageResponse;
import com.stoom.demo.services.MessageService;
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
@RequestMapping("/stoom/message")
@Validated
@Api(value = "MessageController")
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/")
    @PreAuthorize(value = "hasRole('ROLE_CUSTOMER') or hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<HttpStatus> createMessage(@Valid @RequestBody MessageRequest messageRequest){
        return messageService.createMessage(messageRequest);
    }

    @GetMapping("/")
    @PreAuthorize(value = "hasRole('ROLE_CUSTOMER') or hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<MessageResponse>> getAllUserMessages(@Valid @RequestParam String userID){
        return messageService.getAllUserMessages(userID);
    }
}
