package com.example.manager_app.serviceDemo.scope;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Post_Pre_Destroy {
    @PostConstruct
    //ApplicationContext sẽ gọi hàm này sau khi một Bean được tạo ra và quản lý.
    public void postConstruct(){
        System.out.println("\t>> Đối tượng sau khi khởi tạo xong sẽ chạy hàm này");
    }
    public Post_Pre_Destroy() {
        System.out.println("gdhguyguyd");
    }

    public void destroy(){
        System.out.println("CAY");
        System.exit(0);
    }

    @PreDestroy //ApplicationContext sẽ gọi hàm này trước khi một Bean bị xóa hoặc không được quản lý nữa.
    public  void preDestroy(){
        System.out.println("Đối tượng  trước khi bị destroy thì chạy hàm này");
    }
}
