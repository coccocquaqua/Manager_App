package com.example.manager_app.dtoDemo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
@Configuration //đánh dấu đây là một lớp cấu hình
@EnableConfigurationProperties //kích hoạt việc sử dụng các properties trong lớp cấu hình
@ConfigurationProperties("demo1") //tiêm các properties có tiền tố là demo1 vào lớp cấu hình
public class Demo1Config {
    private String environment;
    private List<String> hosts = new ArrayList<>();

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }
}
