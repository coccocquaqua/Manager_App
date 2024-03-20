package com.example.manager_app.restcontroller;

import com.example.manager_app.model.Users;
import com.example.manager_app.repository.UserRepository;
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

    @GetMapping
    public ResponseEntity<?> getAllPage() {
        List<Users> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    @PostMapping
    public ResponseEntity<Users> postUsers(@RequestBody Users user) {
        // Mã hóa mật khẩu của người dùng trước khi lưu vào cơ sở dữ liệu
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
