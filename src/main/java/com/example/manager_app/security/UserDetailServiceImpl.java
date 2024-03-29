package com.example.manager_app.security;

import com.example.manager_app.model.Users;
import com.example.manager_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String Username) throws UsernameNotFoundException {
        Optional<Users> users = userRepository.findUsersByUsername(Username);
        if (!users.isPresent()) {
            System.out.println("User not found!" + Username);

            throw new UsernameNotFoundException("User " + Username + " was not found in the database");
        }
        System.out.println("Found user! " + Username);
        Users user = users.get();
        UserDetails userDetails;
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
        if (users.get().getGoogleId() != null && !users.get().getGoogleId().isEmpty()) {
            userDetails = new User(users.get().getUsername(),"", authorities);
            return userDetails;
        }
        userDetails = new User(users.get().getUsername(), users.get().getPassword(), authorities);
        System.out.println(userDetails + "hehe");
        return userDetails;
    }
}
