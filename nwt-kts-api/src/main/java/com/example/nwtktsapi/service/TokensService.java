package com.example.nwtktsapi.service;


import com.example.nwtktsapi.exceptions.UserNotFoundException;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokensService {

    @Autowired
    private UserRepository userRepository;

    public TokensService() {
    }

    public TokensService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public float getTokensForUser(Long userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            return user.get().getTokens();
        }
        else{
            throw new UserNotFoundException();
        }
    }

    public float addTokensForUser(User user,float tokens){
        float newValue = user.getTokens();
        if( tokens > 0){
            newValue += tokens;
            user.setTokens(newValue);
            userRepository.save(user);
        }
        return newValue;
    }

    public void removeTokensFromUser(User user, float tokens){
        float newValue = user.getTokens();
        newValue -= tokens;
        user.setTokens(newValue);
        userRepository.save(user);
    }

    public void removeTokensFromUsers(List<User> users, float tokens){
        for (User user:users ) {
            removeTokensFromUser(user,tokens/users.size());
        }
    }
}
