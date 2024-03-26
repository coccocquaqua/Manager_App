package com.example.manager_app.dto;

import com.example.manager_app.model.Project;
import com.example.manager_app.model.Users;

import java.util.List;

public class AddProjectRequest {
    private Project project;
    private List<UserProjectReponse> users;
    private String role;

    public AddProjectRequest() {
    }

    public AddProjectRequest(Project project, List<UserProjectReponse> users, String role) {
        this.project = project;
        this.users = users;
        this.role = role;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<UserProjectReponse> getUsers() {
        return users;
    }

    public void setUsers(List<UserProjectReponse> users) {
        this.users = users;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
