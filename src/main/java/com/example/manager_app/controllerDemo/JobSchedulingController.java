package com.example.manager_app.controllerDemo;

import com.example.manager_app.dtoDemo.TaskDefinition;
import com.example.manager_app.serviceDemo.schedule.TaskDefinitionBean;
import com.example.manager_app.serviceDemo.schedule.TaskSchedulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/schedule")
public class JobSchedulingController {
    @Autowired
    private TaskSchedulingService taskSchedulingService;

    @Autowired
    private TaskDefinitionBean taskDefinitionBean;

    @PostMapping()
    public void scheduleATask(@RequestBody TaskDefinition taskDefinition) {
        taskDefinitionBean.setTaskDefinition(taskDefinition);
        taskSchedulingService.scheduleATask(taskDefinition.getActionType(), taskDefinitionBean, taskDefinition.getCronExpression());
    }

    @GetMapping(path="/remove/{jobid}")
    public void removeJob(@PathVariable String jobid) {
        taskSchedulingService.removeScheduledTask(jobid);
    }
}
