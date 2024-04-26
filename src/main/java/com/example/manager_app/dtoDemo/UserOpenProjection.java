package com.example.manager_app.dtoDemo;

import org.springframework.beans.factory.annotation.Value;

public interface UserOpenProjection {
    @Value("#{target.username + ' có role là : ' + target.role}") //sử dụng SpEL để thêm thông tin vào trường thông tin
    // taget.username là trường username trong entity Users
    String getInformation();
}
