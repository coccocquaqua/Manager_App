package com.example.manager_app.dto;

import com.example.manager_app.model.Project;
import com.example.manager_app.model.Users;

import java.util.List;

public class AddUserRequest {
    private Users user;
    private List<ProjectByUserRespone> projects;
    private String role;

    public AddUserRequest(Users user, List<ProjectByUserRespone> projects) {
        this.user = user;
        this.projects = projects;
    }

    public AddUserRequest() {
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<ProjectByUserRespone> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectByUserRespone> projects) {
        this.projects = projects;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
