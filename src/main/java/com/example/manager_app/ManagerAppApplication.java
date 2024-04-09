package com.example.manager_app;

import com.example.manager_app.model.Account;
import com.example.manager_app.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class ManagerAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerAppApplication.class, args);
    }

}
