package com.example.manager_app.dto;

import com.example.manager_app.model.Users;
import lombok.Data;

import java.util.List;

@Data
public class ProjectByIdResponse {
    private Integer id;
    private String name;
    private String description;
    private List<UserProjectReponse> users;
    private String role;
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
