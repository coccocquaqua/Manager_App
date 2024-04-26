package com.example.manager_app.controllerDemo;

import com.example.manager_app.dtoDemo.JobRequest;
import com.example.manager_app.serviceDemo.quartzSchedule.SampleJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("api/quartz")
public class QuartzScheduleController {
    @Autowired
    private Scheduler scheduler;
    @PostMapping("/job")
    public ResponseEntity<String> addJob(@RequestBody JobRequest jobRequest) {
        try {
            JobDetail jobDetail = buildJobDetail(jobRequest);
            Trigger trigger = buildJobTrigger(jobRequest);
            scheduler.scheduleJob(jobDetail, trigger);
            return ResponseEntity.status(HttpStatus.CREATED).body("Job scheduled successfully");
        } catch (SchedulerException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error scheduling job: " + ex.getMessage());
        }
    }
    @PutMapping("/job")
    public ResponseEntity<String> updateJob(@RequestBody JobRequest jobRequest) {
        try {
            JobKey jobKey = new JobKey(jobRequest.getJobName(), jobRequest.getJobGroup());
            if (scheduler.checkExists(jobKey)) {
                // If the job already exists, reschedule it
                Trigger trigger = buildJobTrigger(jobRequest);
                scheduler.rescheduleJob(trigger.getKey(), trigger);
                return ResponseEntity.status(HttpStatus.OK).body("Job rescheduled successfully");
            } else {
                // If the job doesn't exist, create a new one
                JobDetail jobDetail = buildJobDetail(jobRequest);
                Trigger trigger = buildJobTrigger(jobRequest);
                scheduler.scheduleJob(jobDetail, trigger);
                return ResponseEntity.status(HttpStatus.CREATED).body("Job scheduled successfully");
            }        } catch (SchedulerException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error scheduling job: " + ex.getMessage());
        }
    }
    @DeleteMapping("/jobName/{jobName}/jobGroup/{jobGroup}")
    public ResponseEntity<String> deleteJob(@PathVariable String jobName, @PathVariable String jobGroup) {
        try {
            JobKey jobKey = new JobKey(jobName, jobGroup);
            System.out.println(jobKey.getName() + " " + jobKey.getGroup());
            System.out.println(scheduler.checkExists(jobKey));
            if(!scheduler.checkExists(jobKey)) { //
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found");
            }
            scheduler.deleteJob(jobKey);
            return ResponseEntity.ok("Job deleted successfully");
        } catch (SchedulerException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting job: " + ex.getMessage());
        }
    }
    private JobDetail buildJobDetail(JobRequest jobRequest) { // JobDetail là một lớp hỗ trợ lưu trữ thông tin về một công việc cụ thể.
        JobDataMap jobDataMap = new JobDataMap(); // JobDataMap là một lớp hỗ trợ lưu trữ dữ liệu cho một công việc cụ thể
        // Build and return JobDetail instance
        return JobBuilder.newJob(SampleJob.class) // JobBuilder là một lớp hỗ trợ tạo ra một JobDetail mới, chỉdđịnh SampleJob là lớp thực thi công viê
                .withIdentity(jobRequest.getJobName(), jobRequest.getJobGroup()) // withIdentity() đặt tên cho công việc
                .usingJobData(jobDataMap) // usingJobData() thiết lập dữ liệu cho công việc
                .storeDurably() // storeDurably() cho biết công việc này có thể được lưu trữ trong bộ nhớ
                .build();
    }

    private Trigger buildJobTrigger(JobRequest jobRequest) { // Trigger là một cơ chế để kích hoạt một công việc cụ thể.
        return TriggerBuilder.newTrigger()
                .withIdentity(jobRequest.getJobName(), jobRequest.getJobGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(jobRequest.getCronExpression()))// withSchedule() thiết lập lịch trình cho Trigger
                .build();
    }
}
