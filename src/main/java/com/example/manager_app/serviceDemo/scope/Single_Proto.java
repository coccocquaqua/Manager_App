package com.example.manager_app.serviceDemo.scope;

import com.example.manager_app.dtoDemo.Hello;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
@Configuration
public class Single_Proto {
    @Bean
    @Scope("singleton")
    public Hello singleTon(){
        return new Hello();
    }
    @Bean
    @Scope("prototype")
    public Hello ProtoType(){
        return new Hello();
    }
}
