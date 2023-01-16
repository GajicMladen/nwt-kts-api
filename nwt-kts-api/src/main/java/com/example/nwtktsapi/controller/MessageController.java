package com.example.nwtktsapi.controller;

import com.example.nwtktsapi.dto.MessageDTO;
import com.example.nwtktsapi.model.Message;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.service.MessageService;
import com.example.nwtktsapi.service.TransformToDTOService;
import com.example.nwtktsapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "api/messages/")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransformToDTOService transformToDTOService;

    @GetMapping(value = "all")
    public ResponseEntity<List<Object>> getAllMessages(){
        List<Message> messages =  messageService.getAllMessages();
        List<Object> ret = transformToDTOService.transformToDTOList(messages);
        return  ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    @GetMapping(value = "forUser/{id}")
    public ResponseEntity<List<Object>> getMessagesForUser(@PathVariable int id){
        List<Message> messages =  messageService.getMessagesForUser((long) id);
        List<Object> ret = transformToDTOService.transformToDTOList(messages);
        return ResponseEntity.status(HttpStatus.OK).body(ret);
    }

    @PostMapping(value = "/newMessage",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> addNewMessage(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestBody MessageDTO messageDTO){
        User user = userService.getUserById(messageDTO.getUserId());
        Message message = messageService.addNewMessage(messageDTO,user);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }


    @GetMapping(value = "/lastMessageForUsers")
    public ResponseEntity<List<Object>> getLastMessages(){
        List<Message> messages = messageService.getLastMessages();
        List<Object> messageDTOS = transformToDTOService.transformToDTOList(messages);

        return ResponseEntity.status(HttpStatus.OK).body(messageDTOS);
    }
}
