package com.rafi.jwttoken.model;

public class AuthResponse {
    private String token;
    private String refreshToken;

    public AuthResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}