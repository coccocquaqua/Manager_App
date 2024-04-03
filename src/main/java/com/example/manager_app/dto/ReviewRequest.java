package com.example.manager_app.dto;

import com.example.manager_app.model.Retro;
import com.example.manager_app.model.Users;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ReviewRequest {
    private Integer id;
    private Users reviewerId;
    private Retro retroId;
    private Users revieweeId;
    private Double rate;
    private String comment;
    private LocalDate reviewDate;
}
