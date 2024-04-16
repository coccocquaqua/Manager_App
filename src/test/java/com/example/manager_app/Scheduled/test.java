package com.example.manager_app.Scheduled;

import com.example.manager_app.config.ScheduledTasks;
import org.junit.jupiter.api.Test;

public class test {
    @Test
    public void test() {
        ScheduledTasks scheduledTasks = new ScheduledTasks();
        scheduledTasks.runEveryMinute();
        assert true;
    }
}
