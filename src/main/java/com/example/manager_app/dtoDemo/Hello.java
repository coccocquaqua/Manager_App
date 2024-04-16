package com.example.manager_app.dtoDemo;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
@Data
public class Hello {
    private String message;


    public Hello() {
        System.out.println("ok");
    }

    void init(){
        System.out.println("pllplpl");
    }

    public Hello(String message ) {
        this.message = message;
    }


    public void setMessage(String message) {
        this.message = message;
    }
}
