package com.example.nwtktsapi.service;

import com.example.nwtktsapi.dto.UserDTO;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
