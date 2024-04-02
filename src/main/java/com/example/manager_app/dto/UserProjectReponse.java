package com.example.manager_app.dto;

import lombok.Data;

@Data
public class UserProjectReponse {
    private Integer id;
    private String username;
    private String email;
    private String role;
    private Integer status;

    public UserProjectReponse(Integer id, String username, String email, String role, Integer status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public UserProjectReponse() {
    }
}
