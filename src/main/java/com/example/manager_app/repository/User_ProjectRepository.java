package com.example.manager_app.repository;

import com.example.manager_app.model.User_Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface User_ProjectRepository extends JpaRepository<User_Project, Integer> {
    List<User_Project>findUser_ProjectByProjectId(Integer projectId);
}
