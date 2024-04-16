package com.example.manager_app.serviceDemo.schedule;

import com.example.manager_app.dtoDemo.TaskDefinition;
import com.example.manager_app.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskDefinitionBean implements Runnable{
    @Autowired
    EmailService emailService;
    private TaskDefinition taskDefinition;
    @Override
    public void run() {
        System.out.println("TaskDefinitionBean run");
       // emailService.sendSimpleMessage();
    }
    public TaskDefinition getTaskDefinition() {
        return taskDefinition;
    }

    public void setTaskDefinition(TaskDefinition taskDefinition) {
        this.taskDefinition = taskDefinition;
    }
}
