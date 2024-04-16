package com.example.manager_app.controllerDemo;

import com.example.manager_app.dtoDemo.Hello;
import com.example.manager_app.serviceDemo.scope.Request_session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/scope")
public class RequestScope {

    @Autowired
    private Request_session request_session;

    @GetMapping("/request")
    public String hello() {
        // Sử dụng bean với phạm vi request scope
        String a = request_session.requestScopedBean().toString();
        return a;
    }

    @GetMapping("/session")
    public String hello1() {
    request_session.sessionScopedBean().setMessage("OK");
        return request_session.sessionScopedBean().getMessage();
    }

    @GetMapping("/session-update")
    public String hello1update() {
        request_session.sessionScopedBean().setMessage("not ok");
        return request_session.sessionScopedBean().getMessage();
    }
}
