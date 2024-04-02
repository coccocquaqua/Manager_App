package com.example.manager_app.repository;

import com.example.manager_app.model.User_Project;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface User_ProjectRepository extends JpaRepository<User_Project, Integer> {
    List<User_Project> findUser_ProjectByProjectId(Integer projectId);

    List<User_Project> findUser_ProjectByUsersId(Integer userId);

    List<User_Project> findUser_ProjectByProjectIdAndUsersIdAndStatus(Integer projectId,Integer userId,Integer status);
    List<User_Project> findUser_ProjectByProjectIdAndStatus(Integer projectId,Integer status);
    @Transactional
    void deleteUser_ProjectByUsersIdAndProjectId(Integer userId, Integer projectId);
    @Modifying
    @Transactional
    @Query("UPDATE User_Project up SET up.status = :status WHERE up.users.id = :userId AND up.project.id = :projectId")
    void updateStatusByUsersIdAndProjectId(@Param("status") Integer status, @Param("userId") Integer userId, @Param("projectId") Integer projectId);
    @Transactional
    @Modifying
    @Query("UPDATE User_Project up SET up.status = :status WHERE up.id = :userProjectId")
    void updateStatusById(@Param("status") Integer status, @Param("userProjectId") Integer userProjectId);
}
