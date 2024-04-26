package com.example.manager_app.dtoDemo;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventListener_vd {
//    @EventListener sẽ lắng nghe mọi sự kiện xảy ra
//    Nếu có một sự kiện DoorBellEvent được bắn ra, nó sẽ đón lấy
//    và đưa vào hàm để xử lý
@EventListener
public void doorBellEventListener(Event event) throws InterruptedException {

    Thread.sleep(1000);
    // Sự kiện Event được lắng nghe và xử lý tại đây
    System.out.println( "nghe thấy rô nè ");
}
}
