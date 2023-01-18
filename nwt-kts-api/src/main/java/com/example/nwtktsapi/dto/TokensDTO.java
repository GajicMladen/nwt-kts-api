package com.example.nwtktsapi.dto;

import java.io.Serializable;

public class TokensDTO implements Serializable {

    private Long userId;

    private float tokens;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public float getTokens() {
        return tokens;
    }

    public void setTokens(float tokens) {
        this.tokens = tokens;
    }
}
