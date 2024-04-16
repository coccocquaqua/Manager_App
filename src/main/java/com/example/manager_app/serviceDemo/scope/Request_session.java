package com.example.manager_app.serviceDemo.scope;

import com.example.manager_app.dtoDemo.Hello;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
public class Request_session {
    @Bean
    @RequestScope // mỗi lần request sẽ khởi tạo lại đối tượng
    public Hello requestScopedBean() {
        return new Hello();
    }
    @Bean
    @SessionScope //chỉ khởi tạo tạo đối tượng lần đầu tiên trong 1 phiên
    public Hello sessionScopedBean() {
        return new Hello();
    }
    @Bean
    @ApplicationScope // giống singleton nhưng  cho nhiều ứng dụng đang chạy trong cùng một ServletContext
    public Hello ApplicationScopedBean() {
        return new Hello();
    }
}
