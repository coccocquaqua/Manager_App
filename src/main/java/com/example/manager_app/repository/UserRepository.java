package com.example.manager_app.repository;

import com.example.manager_app.dtoDemo.UserCloseProjection;
import com.example.manager_app.dtoDemo.UserDTO;
import com.example.manager_app.dtoDemo.UserOpenProjection;
import com.example.manager_app.model.Users;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findUsersByUsername(String username);

    Optional<Users> findUsersByEmailAndUsername(String email, String username);

    //JPQL
    @Query("SELECT u FROM Users u WHERE u.username = :username")
    Optional<Users> findUsersByUsernamesJPQL(@Param("username") String username);


    //Native SQL Query
    //@Modifying sử dụng cho các câu lệnh insert, update, delete sử dụng để chỉ ra rằng câu lệnh này sẽ thay đổi dữ liệu trong cơ sở dữ liệu
    @Query(value = "SELECT * FROM Users WHERE username = :username", nativeQuery = true)
    Optional<Users> findUsersByUsernames(@Param("username") String username);

    //Class Constructors trả về đối tượng cụ thể từ câu truy vấn SQL hoặc JPQL bằng cách sử dụng constructor của class
    @Query("SELECT new com.example.manager_app.dtoDemo.UserDTO(u.username, u.email) FROM Users u WHERE u.username = :username")
    Optional<UserDTO> findUserDtoByUsername(@Param("username") String username);

    //Join Tables
    @Query(value = "SELECT u FROM Users u JOIN User_Project up ON u.id = up.users.id JOIN Project p ON up.project.id = p.id WHERE p.id = :projectId")
    List<Users> findUsersByProjectId(@Param("projectId") Integer projectId);

    @Query("SELECT u FROM Users u")
    List<Users> findAllSortUsers(Sort sort);

    //paging JPQL Query
    @Query("SELECT u FROM Users u")
    Page<Users> findAllUsersWithPagination(Pageable pageable);

    //paging Native SQL Query
    @Query(value = "SELECT * FROM Users", nativeQuery = true)
    Page<Users> findAllUsersWithPaginationNative(Pageable pageable);

    //    Slice JPQL Query
    @Query("SELECT u FROM Users u")
    Slice<Users> findAllUsersWithPaginationSlice(Pageable pageable);

    //    Slice Native SQL Query
    @Query(value = "SELECT * FROM Users", nativeQuery = true)
    Slice<Users> findAllUsersWithPaginationNativeSlice(Pageable pageable);

    @Query("SELECT u FROM Users u WHERE u.username = :username")
    Optional<Users> findUsersByUsernamesJPQLCache(@Param("username") String username);

    @Modifying //sử dụng cho các câu lệnh insert, update, delete sử dụng để chỉ ra rằng câu lệnh này sẽ thay đổi dữ liệu trong cơ sở dữ liệu
    //Close Projection
    List<UserCloseProjection> findByEmail(String email);
    //Open Projection
    UserOpenProjection findByUsername(String username);
}
