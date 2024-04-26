package com.example.manager_app.dtoDemo;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;

    public UserDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
