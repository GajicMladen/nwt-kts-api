package com.example.nwtktsapi.service;

import com.example.nwtktsapi.constants.Constants;
import com.example.nwtktsapi.exceptions.UserNotFoundException;
import com.example.nwtktsapi.model.User;
import com.example.nwtktsapi.repository.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class TokenServiceTest {


    private TokensService tokensService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        when(userRepository.findById(Constants.VALID_CLIENT_ID)).thenReturn(Optional.of(Constants.testUser));
        when(userRepository.findById(Constants.INVALID_CLIENT_ID)).thenReturn(Optional.empty());
        tokensService = new TokensService(userRepository);
    }

    @Test
    public void getTokensForUser_shouldBeError() {
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            tokensService.getTokensForUser(Constants.INVALID_CLIENT_ID);
        });
    }

    @Test
    public void getTokensForUser_shouldGetZero() throws UserNotFoundException {
        Constants.testUser.setTokens(0);
        float tokens = tokensService.getTokensForUser(Constants.VALID_CLIENT_ID);
        assertEquals(0, tokens);
    }
    @Test
    public void getTokensForUser_shouldBe100() throws UserNotFoundException {
        Constants.testUser.setTokens(100);
        float tokens = tokensService.getTokensForUser(Constants.VALID_CLIENT_ID);
        assertEquals(100, tokens);
    }


    @Test
    public void addTokensForUser_shouldBe100() throws UserNotFoundException {
        Constants.testUser.setTokens(90);
        float totalTokens = tokensService.addTokensForUser(Constants.testUser,10);
        assertEquals(100, totalTokens);
        totalTokens = tokensService.getTokensForUser(Constants.VALID_CLIENT_ID);
        assertEquals(100, totalTokens);
    }

    @Test
    public void removeTokensFromUser_shouldBe90() throws UserNotFoundException {
        Constants.testUser.setTokens(100);
        tokensService.removeTokensFromUser(Constants.testUser,10);
        float totalTokens = tokensService.getTokensForUser(Constants.VALID_CLIENT_ID);
        assertEquals(90, totalTokens);
    }

    @Test
    public void removeTokensFromUsers_shouldBe90() throws UserNotFoundException {
        Constants.testUser.setTokens(100);
        Constants.testClient.setTokens(100);
        Constants.testDriver.setTokens(100);
        List<User> users = new ArrayList<>();
        users.add(Constants.testUser);
        users.add(Constants.testClient);
        users.add(Constants.testDriver);
        tokensService.removeTokensFromUsers(users,30);
        assertEquals(90, Constants.testUser.getTokens());
        assertEquals(90, Constants.testDriver.getTokens());
        assertEquals(90, Constants.testClient.getTokens());
    }
}
