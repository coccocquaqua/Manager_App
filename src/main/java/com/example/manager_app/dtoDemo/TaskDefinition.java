package com.example.manager_app.dtoDemo;

import lombok.Data;

@Data
public class TaskDefinition {
    private String cronExpression;
    private String actionType;
}
