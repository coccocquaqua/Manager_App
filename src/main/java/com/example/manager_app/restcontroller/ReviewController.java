package com.example.manager_app.restcontroller;

import com.example.manager_app.dto.*;
import com.example.manager_app.model.Project;
import com.example.manager_app.model.Review;
import com.example.manager_app.model.Users;
import com.example.manager_app.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/Review")
public class ReviewController {
    @Autowired
    ReviewService reviewService;
    @GetMapping("")

    public ResponseEntity<?> getAllPage(@RequestParam(defaultValue = "1") int page) {
        if (page < 1) page = 1;
        int pageNumber = page - 1;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<ReviewResponse> reviewResponses = reviewService.getAll(pageable);
        return ResponseEntity.ok(reviewResponses);
    }
    @GetMapping("/project/{projectId}")

    public ResponseEntity<?> getReviewByProject(@PathVariable Integer projectId) {

        List<ReviewResponse> reviewResponses = reviewService.getReviewByProjectId(projectId);
        return ResponseEntity.ok(reviewResponses);
    }
    @GetMapping("/user/{userId}")

    public ResponseEntity<?> getReviewByUser(@PathVariable Integer userId) {
        List<ReviewResponse> reviewResponses = reviewService.getReviewByUserId(userId);
        return ResponseEntity.ok(reviewResponses);
    }
    @GetMapping("/date/{date}")

    public ResponseEntity<?> getReviewByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<ReviewResponse> reviewResponses = reviewService.getReviewByReviewDate(date);
        return ResponseEntity.ok(reviewResponses);
    }
    @PostMapping
    public ResponseEntity<?> post(@RequestBody ReviewRequest review) {
        ReviewResponse reviewResponses = reviewService.addReviewUser(review);
        return ResponseEntity.ok(reviewResponses);
    }
}
