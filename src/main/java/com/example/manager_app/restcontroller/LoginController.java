package com.example.manager_app.restcontroller;

import com.example.manager_app.dto.JwtRequest;
import com.example.manager_app.dto.MessageResponse;
import com.example.manager_app.dto.UserInfoResponse;
import com.example.manager_app.repository.UserRepository;
import com.example.manager_app.security.JwtUtils;
import com.example.manager_app.security.UserDetailServiceImpl;
import com.example.manager_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class LoginController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) throws Exception,BadCredentialsException {
        try {
            Authentication authentication = authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            System.out.println("hihi" + userDetails);
            String token = jwtUtils.generateToken(userDetails);
            String refreshToken = jwtUtils.generateRefreshToken(userDetails);
            UserInfoResponse userInfoResponse = new UserInfoResponse(userDetails.getUsername(), token, refreshToken);
            return ResponseEntity.ok(userInfoResponse);
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    private Authentication authenticate(String username, String password) throws Exception {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        try {
            // Lấy thông tin người dùng từ refreshToken
            String username = jwtUtils.getUsernameFromToken(refreshToken);
            UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(username);

            // Tạo accessToken mới từ refreshToken
            String newAccessToken = jwtUtils.generateToken(userDetails);
            String newRefreshToken = jwtUtils.generateRefreshToken(userDetails);
            UserInfoResponse userInfoResponse = new UserInfoResponse(userDetails.getUsername(), newAccessToken, newRefreshToken);
            // Trả về accessToken mới
            return ResponseEntity.ok(userInfoResponse);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error refreshing token: " + e.getMessage());
        }
    }

    //    @GetMapping
//    public String welcome() {
//        return "Welcome to Google !!";
//    }
    @GetMapping()
    public ResponseEntity<?> welcome(Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();
            // Kiểm tra xem người dùng có đăng nhập bằng Google không
            if (attributes.containsKey("email")) {
                // Trả về token hoặc thông tin tùy chỉnh tại đây
                UserDetails userDetails = (UserDetails) oAuth2User;
                System.out.println("hihi" + userDetails);
                String token = jwtUtils.generateToken(userDetails);
                String refreshToken = jwtUtils.generateRefreshToken(userDetails);
                UserInfoResponse userInfoResponse = new UserInfoResponse(userDetails.getUsername(), token, refreshToken);
                return ResponseEntity.ok(userInfoResponse);
            }
        }
        return ResponseEntity.badRequest().body("Error refreshing token: ");
    }

    @GetMapping("/user")
    public Principal user(Principal principal) {
        System.out.println("username : " + principal.getName());
        return principal;
    }

    @GetMapping("/google")
    public ResponseEntity<?> loginGoogle(@AuthenticationPrincipal OAuth2User authenticationToken) {
        System.out.println(authenticationToken+"ok");
        try {
            UserInfoResponse userInfoResponse = userService.loginGoogle(authenticationToken);
            System.out.println(userInfoResponse+"user");
            return ResponseEntity.ok(userInfoResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error refreshing token: " + e.getMessage());

        }

    }}
