package com.example.manager_app.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "employee_project")
public class Employee_Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false)
    private String role;
}
