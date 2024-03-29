package com.example.manager_app.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class ReviewResponse {
    private Integer id;
    private String nameUserReviewer;
    private String nameProject;
    private String nameRetro;
    private String nameUserReviewee;
    private Double rate;
    private String comment;
    private LocalDate reviewDate;
}
