package com.example.manager_app.service;

import com.example.manager_app.dto.UserInfoResponse;
import com.example.manager_app.model.Users;
import com.example.manager_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public UserInfoResponse getUserInfo(OAuth2User principal) {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setUsername(principal.getAttribute("username"));
        return userInfoResponse;
    }
}
