package com.example.manager_app;

import com.example.manager_app.config.ScheduledTasks;
import com.example.manager_app.dtoDemo.Demo1Config;
import com.example.manager_app.dtoDemo.EventPuslisher;
import com.example.manager_app.dtoDemo.Hello;
import com.example.manager_app.serviceDemo.scope.Post_Pre_Destroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication // chứa @Configuration, @EnableAutoConfiguration, @ComponentScan
@EnableScheduling
@EnableCaching
public class ManagerAppApplication implements CommandLineRunner {
    @Autowired
    @Qualifier("singleTon") // chỉ định tên muốn sử dụng
    Hello hello1;
    @Autowired
    @Qualifier("singleTon")
    Hello hello2;

    @Autowired
    @Qualifier("ProtoType") // chỉ định tên muốn sử dụng
    Hello hello3;
    @Autowired
    @Qualifier("ProtoType")
    Hello hello4;

    @Autowired
    Post_Pre_Destroy post_pre_destroy;
    @Autowired
    Demo1Config demo1Config;
    @Autowired
    EventPuslisher eventPuslisher;
    public static void main(String[] args) {
        //SpringApplication.run(ManagerAppApplication.class, args);
        System.out.println("> Trước khi IoC Container được khởi tạo");
        ApplicationContext context = SpringApplication.run(ManagerAppApplication.class, args);
       // Appliacation context là một interface đại dện cho container của IOC, chứa các bean và quản lý các bean

        Post_Pre_Destroy girl = context.getBean(Post_Pre_Destroy.class);

        System.out.println("> Trước khi IoC Container destroy Girl");
        ((ConfigurableApplicationContext) context).getBeanFactory().destroyBean(girl);
        System.out.println("> Sau khi IoC Container destroy Girl");

//        ScheduledTasks scheduledTasks=new ScheduledTasks();
//        scheduledTasks.runEveryMinute();
    }
    @Override
    public void run(String... args) {
//        System.out.println("singleton "+(hello1==hello2));
//        System.out.println("prototype "+(hello3==hello4));
        System.out.println(demo1Config.getEnvironment()+"  oki");
        System.out.println(demo1Config.getHosts()+"  oki");
    }
    @Bean
    CommandLineRunner run() {
        return args -> {
            System.out.println( "bắt đầu :");
            // gõ cửa
            eventPuslisher.publisher();
            System.out.println("kết thúc");
        };
    }

}
