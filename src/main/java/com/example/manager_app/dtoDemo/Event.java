package com.example.manager_app.dtoDemo;

import org.springframework.context.ApplicationEvent;

//phải kế thừa lớp ApplicationEvent của Spring
//        Như vậy nó mới được coi là một sự kiện hợp lệ.
public class Event extends ApplicationEvent {
//    Mọi Class kế thừa ApplicationEvent sẽ
//    phải gọi Constructor tới lớp cha.
    public Event(Object source) {
        // Object source là object tham chiếu tới sự kiện đó
        super(source);
    }
}
