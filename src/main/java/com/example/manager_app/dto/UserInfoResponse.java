package com.example.manager_app.dto;

import lombok.Data;

@Data
public class  UserInfoResponse {
    private Integer userId;
    private String username;
    private String token;
    private String refreshToken;

    public UserInfoResponse() {
    }

    public UserInfoResponse(String username, String token, String refreshToken) {
        this.username = username;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public UserInfoResponse(Integer userId, String username, String token, String refreshToken) {
        this.userId = userId;
        this.username = username;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public UserInfoResponse(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
