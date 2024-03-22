package com.example.manager_app.service;

import com.example.manager_app.dto.UserInfoResponse;
import com.example.manager_app.dto.UserProjectReponse;
import com.example.manager_app.model.Project;
import com.example.manager_app.model.Role;
import com.example.manager_app.model.User_Project;
import com.example.manager_app.model.Users;
import com.example.manager_app.repository.ProjectRepository;
import com.example.manager_app.repository.UserRepository;
import com.example.manager_app.repository.User_ProjectRepository;
import com.example.manager_app.security.JwtUtils;
import com.example.manager_app.security.UserDetailServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDetailServiceImpl userDetailService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    User_ProjectRepository user_projectRepository;

    private final ModelMapper modelMapper;

    public UserService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserInfoResponse getUserInfo(OAuth2User principal) {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setUsername(principal.getAttribute("username"));
        return userInfoResponse;
    }

    public UserInfoResponse loginGoogle(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        System.out.println(email + "oke mail");
        String googleId = oAuth2User.getAttribute("sub");
        String name = oAuth2User.getAttribute("name");
        Optional<Users> usersOptional = userRepository.findUsersByEmailAndUsername(email, name);
        Users users;
        if (!usersOptional.isPresent()) {
            users = new Users();
            users.setUsername(name);
            users.setEmail(email);
            users.setGoogleId(googleId);
            users.setPassword(null);
            users.setRole(Role.USER);
            userRepository.save(users);

        } else {
            users = usersOptional.get();
        }
        System.out.println("name kk  " + users.getUsername());
        UserDetails userDetails = userDetailService.loadUserByUsername(users.getUsername());
        System.out.println(userDetails);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);
        UserInfoResponse userInfoResponse = new UserInfoResponse(users.getUsername(), token, refreshToken);
        return userInfoResponse;
    }

    public List<UserProjectReponse> getUserByProject(Integer projectId) {
        Optional<Project> project1 = projectRepository.findById(projectId);
        List<UserProjectReponse> userProjectReponse = new ArrayList<>();
        List<User_Project> user_projects = user_projectRepository.findUser_ProjectByProjectId(projectId);
        System.out.println("kkk" + user_projects);
        for (User_Project userProject : user_projects) {
            Optional<Users> userOptional = userRepository.findById(userProject.getUsers().getId());
            if (userOptional.isPresent()) {
                Users user = userOptional.get();
                userProjectReponse.add(new UserProjectReponse(user.getUsername(), user.getEmail(), userProject.getRole()));
            }
        }


        return userProjectReponse;
    }

    public List<UserProjectReponse> getAll() {
        List<Users> users = userRepository.findAll();
        List<User_Project> userProjects = user_projectRepository.findAll();
        List<UserProjectReponse> list = new ArrayList<>();
        for (Users item : users) {
            UserProjectReponse userProjectReponse = new UserProjectReponse();
            userProjectReponse.setUsername(item.getUsername());
            userProjectReponse.setEmail(item.getEmail());
            for (User_Project item1 : userProjects) {
                if (item1.getUsers().getId() == item.getId()) {
                    userProjectReponse.setRole(item1.getRole());
                    break;
                }
            }
            list.add(userProjectReponse);
        }
        return list;
    }
    public UserInfoResponse addUser(Users users, List<Project>projectList){
        Optional<Users>usersOptional=userRepository.findById(users.getId());

        users.setRole(Role.USER);
       Users users1=userRepository.save(users);

    }
}
