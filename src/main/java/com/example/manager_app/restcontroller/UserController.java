package com.example.manager_app.restcontroller;

import com.example.manager_app.dto.AddUserRequest;
import com.example.manager_app.dto.ProjectByUserRespone;
import com.example.manager_app.dto.UserProjectReponse;
import com.example.manager_app.model.Project;
import com.example.manager_app.model.Users;
import com.example.manager_app.repository.UserRepository;
import com.example.manager_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<?> getAllPage(@RequestParam(defaultValue = "1") int page) {
        if (page < 1) page = 1;
        int pageNumber = page - 1;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<UserProjectReponse> users = userService.getAllPage(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("getall")
    public ResponseEntity<?> getAllPage() {
        List<Users> users = userService.getAll();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getById(@PathVariable Integer userId) {
        Optional<Users> users = userRepository.findById(userId);
        return ResponseEntity.ok(users);
    }
//    @PostMapping
//    public ResponseEntity<UserProjectReponse> post(@RequestBody AddUserRequest userRequest) {
//        Users users = userRequest.getUser();
//        List<ProjectByUserRespone> projectList = userRequest.getProjects();
//        UserProjectReponse userProjectReponse = userService.addUser(users, projectList,userRequest.getRole());
//        return ResponseEntity.ok(userProjectReponse);
//    }
    @PostMapping
    public ResponseEntity<UserProjectReponse>post(@RequestBody Users users){
        return ResponseEntity.ok(userService.addUser(users));
    }

    @GetMapping("/filter-project/{projectId}")
    public ResponseEntity<?> getUserByProject(@PathVariable Integer projectId) {
        System.out.println(projectId + "bbb");
        List<UserProjectReponse> userProjectReponse = userService.getUserByProject(projectId);
        return ResponseEntity.ok(userProjectReponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsers(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody Users users) {
        return ResponseEntity.ok(userService.updateUser(users));
    }

}
