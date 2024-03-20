package com.example.manager_app.repository;

import com.example.manager_app.model.Employee_Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Employee_ProjectRepository extends JpaRepository<Employee_Project, Integer> {
}
