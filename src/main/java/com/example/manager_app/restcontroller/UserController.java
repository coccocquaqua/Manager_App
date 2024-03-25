package com.example.manager_app.restcontroller;

import com.example.manager_app.dto.AddUserRequest;
import com.example.manager_app.dto.ProjectByUserRespone;
import com.example.manager_app.dto.UserProjectReponse;
import com.example.manager_app.model.Project;
import com.example.manager_app.model.Users;
import com.example.manager_app.repository.UserRepository;
import com.example.manager_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/User")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @GetMapping

    public ResponseEntity<?> getAllPage() {
        List<UserProjectReponse> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping
//    public ResponseEntity<Users> postUsers(@RequestBody Users user) {
//        // Mã hóa mật khẩu của người dùng trước khi lưu vào cơ sở dữ liệu
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//        return ResponseEntity.ok(user);
//    }

    public ResponseEntity<UserProjectReponse> post(@RequestBody AddUserRequest userRequest) {
        Users users = userRequest.getUser();
        List<ProjectByUserRespone> projectList = userRequest.getProjects();
        UserProjectReponse userProjectReponse = userService.addUser(users, projectList);
        return ResponseEntity.ok(userProjectReponse);
    }

    @GetMapping("/filter-project/{projectId}")
    public ResponseEntity<?> getUserByProject(@PathVariable Integer projectId) {
        System.out.println(projectId + "bbb");
        List<UserProjectReponse> userProjectReponse = userService.getUserByProject(projectId);
        return ResponseEntity.ok(userProjectReponse);
    }
}
