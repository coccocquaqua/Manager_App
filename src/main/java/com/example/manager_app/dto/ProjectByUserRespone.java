package com.example.manager_app.dto;

public class ProjectByUserRespone {
    private Integer id;
    private String name;
    private String description;
    private String role;

    public ProjectByUserRespone() {
    }

    public ProjectByUserRespone(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProjectByUserRespone(Integer id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
