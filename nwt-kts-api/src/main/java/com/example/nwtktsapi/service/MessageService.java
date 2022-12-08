package com.example.nwtktsapi.service;

import com.example.nwtktsapi.dto.MessageDTO;
import com.example.nwtktsapi.model.Message;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessagesForUser(Long userId){

        return messageRepository.findByUser_Id(userId);

    }

    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    public Message addNewMessage(MessageDTO messageDTO, User user){
        Message message = new Message(messageDTO,user);
        return messageRepository.save(message);
    }

    public Message saveMessage(Message message){
        return messageRepository.save(message);
    }
}
