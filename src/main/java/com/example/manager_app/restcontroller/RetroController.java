package com.example.manager_app.restcontroller;

import com.example.manager_app.model.Retro;
import com.example.manager_app.service.RetroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/Retro")
public class RetroController {
    @Autowired
    RetroService retroService;
    @GetMapping("/end-date")
    public ResponseEntity<?> getInProgressReviews() {
        List<Retro>list=retroService.getRetroByEndDate();
        return ResponseEntity.ok(list);
    }
}
