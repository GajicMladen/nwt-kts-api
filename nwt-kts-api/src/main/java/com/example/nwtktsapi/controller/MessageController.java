package com.example.nwtktsapi.controller;

import com.example.nwtktsapi.dto.MessageDTO;
import com.example.nwtktsapi.model.Message;
import com.example.nwtktsapi.service.MessageService;
import com.example.nwtktsapi.service.TransformToDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "api/messages/")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private TransformToDTOService transformToDTOService;

    @GetMapping(value = "all")
    public ResponseEntity<List<Object>> getAllMessages(){
        List<Message> messages =  messageService.getAllMessages();
        List<Object> ret = transformToDTOService.transformToDTOList(messages);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping(value = "forUser/{id}")
    public ResponseEntity<List<Object>> getMessagesForUser(@PathVariable int id){
        List<Message> messages =  messageService.getMessagesForUser((long) id);
        List<Object> ret = transformToDTOService.transformToDTOList(messages);
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }
}
