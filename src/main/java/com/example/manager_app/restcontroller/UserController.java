package com.example.manager_app.restcontroller;

import com.example.manager_app.dto.AddUserRequest;
import com.example.manager_app.dto.ProjectByUserRespone;
import com.example.manager_app.dto.UserProjectReponse;
import com.example.manager_app.dtoDemo.UserCloseProjection;
import com.example.manager_app.dtoDemo.UserOpenProjection;
import com.example.manager_app.model.Project;
import com.example.manager_app.model.Users;
import com.example.manager_app.repository.UserRepository;
import com.example.manager_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

    @GetMapping("/admin")

    public ResponseEntity<?> getAllPage(@RequestParam(defaultValue = "1") int page) {
        if (page < 1) page = 1;
        int pageNumber = page - 1;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<UserProjectReponse> users = userService.getAllPage(pageable);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/admin-pm/getall")
    public ResponseEntity<?> getAllPage() {
        List<Users> users = userService.getAll();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/admin/user/{userId}")
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
    @PostMapping("/admin")
    public ResponseEntity<UserProjectReponse>post(@RequestBody Users users){
        return ResponseEntity.ok(userService.addUser(users));
    }

    @GetMapping("/admin/filter-project/{projectId}")
    public ResponseEntity<?> getUserByProject(@PathVariable Integer projectId) {
        System.out.println(projectId + "bbb");
        List<UserProjectReponse> userProjectReponse = userService.getUserByProject(projectId);
        return ResponseEntity.ok(userProjectReponse);
    }
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteUsers(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/admin")
    public ResponseEntity<?> updateUser(@RequestBody Users users) {
        return ResponseEntity.ok(userService.updateUser(users));
    }
    @GetMapping("/admin/sort")
    public ResponseEntity<?> getAllSort() {
        List<Users> users = userService.getAllSortUsers();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/admin/slice")
    public Slice<Users> getUsersSlice(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {
        return userService.getUsersSlice(page, size);
    }
    @GetMapping("/admin/cache/{username}")
    public Optional<Users> getUserByUsername(@PathVariable String username) {
        return userService.CacheAble(username);
    }
    @DeleteMapping("/admin/cache/{username}")
    public ResponseEntity<?> deleteUserByUsername(@PathVariable String username) {
        userService.CacheEvict(username);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/admin/cache/{username}")
    public Optional<Users> putUserByUsername(@PathVariable String username) {
        return userService.CachePut(username);
    }
    @GetMapping("/admin/close/{email}")
    public ResponseEntity<?> getClose(@PathVariable String email) {
        List<UserCloseProjection> users = userService.getUsersClose(email);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/admin/open/{username}")
    public ResponseEntity<?> getOpen(@PathVariable String username) {
        UserOpenProjection users = userService.getUsersByUsernamesOpen(username);
        return ResponseEntity.ok(users);
    }
}
