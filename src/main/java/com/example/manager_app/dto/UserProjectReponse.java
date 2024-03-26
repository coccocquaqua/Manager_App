package com.example.manager_app.dto;

public class UserProjectReponse {
    private Integer id;
    private String username;
    private String email;
    private String role;

    public UserProjectReponse() {
    }

    public UserProjectReponse(String username, String email, String role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public UserProjectReponse(Integer id, String username, String email, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
