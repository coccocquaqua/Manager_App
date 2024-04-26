package com.example.manager_app.config;

import com.example.manager_app.serviceDemo.quartzSchedule.SampleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
//    @Bean
//    public JobDetail sampleJobDetail() { // JobDetail là một công việc cụ thể mà bạn muốn thực hiện, nó chứa thông tin về việc thực hiện công việc đó.
//        return JobBuilder.newJob(SampleJob.class) // JobBuilder là một lớp hỗ trợ tạo ra một JobDetail mới.
//                .withIdentity("ScheduleSimple") // withIdentity() đặt tên cho công việc
//                .storeDurably() // storeDurably() cho biết công việc này có thể được lưu trữ trong bộ nhớ
//                .build(); // build() tạo ra một JobDetail mới
//    }

//    @Bean
//    public Trigger sampleJobTrigger(JobDetail sampleJobDetail) { // Trigger là một cơ chế để kích hoạt một công việc cụ thể.
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule() // SimpleScheduleBuilder là một lớp hỗ trợ tạo ra một lịch trình đơn giản
//                .withIntervalInSeconds(10) // withIntervalInSeconds() thiết lập khoảng thời gian giữa các lần thực thi công việc
//                .repeatForever(); // repeatForever() thiết lập công việc sẽ được thực thi vô hạn số lần
//
//        return TriggerBuilder.newTrigger() // TriggerBuilder là một lớp hỗ trợ tạo ra một Trigger mới
//                .forJob(sampleJobDetail) // forJob() thiết lập công việc mà Trigger sẽ kích hoạt
//                .withIdentity("sampleTrigger") // withIdentity() đặt tên cho Trigger
//                .withSchedule(scheduleBuilder) // withSchedule() thiết lập lịch trình cho Trigger
//                .build(); // build() tạo ra một Trigger mới
//    }
}
