package com.example.manager_app.service;

import com.example.manager_app.dto.UserInfoResponse;
import com.example.manager_app.model.Role;
import com.example.manager_app.model.Users;
import com.example.manager_app.repository.UserRepository;
import com.example.manager_app.security.JwtUtils;
import com.example.manager_app.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    JwtUtils jwtUtils;
    public UserInfoResponse getUserInfo(OAuth2User principal) {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setUsername(principal.getAttribute("username"));
        return userInfoResponse;
    }
public  UserInfoResponse loginGoogle(OAuth2User oAuth2User){
String email =oAuth2User.getAttribute("email");
    System.out.println(email+"oke mail");
String googleId=oAuth2User.getAttribute("sub");
String name=oAuth2User.getAttribute("name");
Optional<Users> usersOptional=userRepository.findUsersByEmailAndUsername(email,name);
        Users users;
        if(!usersOptional.isPresent()){
            users=new Users();
            users.setUsername(name);
            users.setEmail(email);
            users.setGoogleId(googleId);
            users.setPassword(null);
            users.setRole(Role.USER);
            userRepository.save(users);

        }else {
            users=usersOptional.get();
        }
    System.out.println( "name kk  " +users.getUsername());
    UserDetails userDetails=userDetailService.loadUserByUsername(users.getUsername());
    System.out.println(userDetails);
    Authentication authentication=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtUtils.generateToken(userDetails);
    String refreshToken=jwtUtils.generateRefreshToken(userDetails);
    UserInfoResponse userInfoResponse=new UserInfoResponse(users.getUsername(), token,refreshToken);
    return userInfoResponse;
    }
}
