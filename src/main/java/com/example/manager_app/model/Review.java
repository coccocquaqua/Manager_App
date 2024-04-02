package com.example.manager_app.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private Users userReviewer;

//    @ManyToOne
//    @JoinColumn(name = "project_id")
//    private Project project;

    @ManyToOne
    @JoinColumn(name = "retro_id")
    private Retro retro;

    @ManyToOne
    @JoinColumn(name = "reviewee_id")
    private Users userReviewee;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "review_date")
    private LocalDate reviewDate;
}
