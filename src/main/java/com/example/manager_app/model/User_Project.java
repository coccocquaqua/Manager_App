package com.example.manager_app.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_project")
public class User_Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false)
    private String role;
}
