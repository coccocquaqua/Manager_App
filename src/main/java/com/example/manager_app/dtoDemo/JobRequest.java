package com.example.manager_app.dtoDemo;

import lombok.Data;

@Data
public class JobRequest {
    private String jobName;
    private String jobGroup;
    private String cronExpression;
}
