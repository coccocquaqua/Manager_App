package com.example.manager_app.repository;

import com.example.manager_app.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findUsersByUsername(String username);

    Optional<Users> findUsersByEmailAndUsername(String email, String username);
//    @Query("SELECT u FROM Users u WHERE u.username = :username")
//    Optional<Users> findUsersByUsernames(@Param("username") String username);
}
