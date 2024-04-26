package com.example.manager_app.dtoDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPuslisher {
    @Autowired
    ApplicationEventPublisher applicationEventPublisher; // Để phát ra sự kiện Event
    //applicationEventPublisher là bean có sẵn trong context của Spring
    public void publisher(){
        // Phát ra một sự kiện Event
        // source (Nguồn phát ra) chính là class này
        applicationEventPublisher.publishEvent(new Event(this));
    }
}
