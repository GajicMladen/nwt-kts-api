package com.example.nwtktsapi.controller;

import com.example.nwtktsapi.dto.TokensDTO;
import com.example.nwtktsapi.exceptions.UserNotFoundException;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.service.TokensService;
import com.example.nwtktsapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "api/tokens/")
public class TokensController {


    @Autowired
    private TokensService tokensService;

    @Autowired
    private UserService userService;


    @GetMapping("forUser/{id}")
    public ResponseEntity<Float> getTokensForUser(@PathVariable Long id){
        try {
            float tokens = tokensService.getTokensForUser(id);
            return new ResponseEntity<>(tokens,HttpStatus.OK);

        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("addTokens")
    public ResponseEntity<Float> addTokens(@RequestBody TokensDTO tokensDTO){

        User user = userService.getUserById(tokensDTO.getUserId());
        if(user != null){
            float total = tokensService.addTokensForUser(user, tokensDTO.getTokens());
            return new ResponseEntity<>(total,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
