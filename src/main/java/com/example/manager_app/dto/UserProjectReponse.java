package com.example.manager_app.dto;

import lombok.Data;

@Data
public class UserProjectReponse {
    private Integer id;
    private String username;
    private String email;
    private String project;
    private String role;
    private String status;

    public UserProjectReponse(Integer id, String username, String email, String role, String status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.status = status;
    }

    public UserProjectReponse(Integer id, String username, String email, String project, String role, String status) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.project = project;
        this.role = role;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserProjectReponse() {
    }
}
