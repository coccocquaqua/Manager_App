package com.example.manager_app.restcontroller;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(value = {AuthenticationException.class})
//    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
//        // Xử lý ngoại lệ xác thực ở đây
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized: " + ex.getMessage());
//    }
@ExceptionHandler(AccessDeniedException.class)
public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException e) {
    Map<String, String> errorResponse = new HashMap<>();
    errorResponse.put("message", "Access denied");
    errorResponse.put("status", String.valueOf(HttpStatus.FORBIDDEN.value()));
    return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
}

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Authentication failed");
        errorResponse.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException(BadCredentialsException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", e.getMessage());
        errorResponse.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}
