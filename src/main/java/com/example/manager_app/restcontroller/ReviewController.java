package com.example.manager_app.restcontroller;

import com.example.manager_app.dto.*;
import com.example.manager_app.model.Project;
import com.example.manager_app.model.Review;
import com.example.manager_app.model.Users;
import com.example.manager_app.repository.ReviewRepository;
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
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/Review")
public class ReviewController {
    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewRepository reviewRepository;

    @GetMapping("/admin/getall")
    public ResponseEntity<?> getAll() {
        List<ReviewResponse> reviewResponses = reviewService.getAll();
        return ResponseEntity.ok(reviewResponses);
    }
    @GetMapping("/admin")
    public ResponseEntity<?> getAllPage(@RequestParam(defaultValue = "1") int page) {
        if (page < 1) page = 1;
        int pageNumber = page - 1;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<ReviewResponse> reviewResponses = reviewService.getAllPage(pageable);
        return ResponseEntity.ok(reviewResponses);
    }
    @GetMapping("/admin/project/{projectId}")

    public ResponseEntity<?> getReviewByProject(@PathVariable Integer projectId) {

        List<ReviewResponse> reviewResponses = reviewService.getReviewByProjectId(projectId);
        return ResponseEntity.ok(reviewResponses);
    }
    @GetMapping("/user/{userId}")

    public ResponseEntity<?> getReviewByUser(@PathVariable Integer userId) {
        List<ReviewResponse> reviewResponses = reviewService.getReviewByReviewerId(userId);
        return ResponseEntity.ok(reviewResponses);
    }
    @GetMapping("/admin/date/{date}")

    public ResponseEntity<?> getReviewByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        List<ReviewResponse> reviewResponses = reviewService.getReviewByReviewDate(date);
        return ResponseEntity.ok(reviewResponses);
    }
    //user
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Review review) {
        ReviewResponse reviewResponses = reviewService.addReviewUser(review);
        return ResponseEntity.ok(reviewResponses);
    }
    //user
    @GetMapping("/getall-user/{userId}")

    public ResponseEntity<?> getReviewUser(@PathVariable Integer userId) {
        List<ReviewResponse> reviewResponses = reviewService.getReviewUser(userId);
        return ResponseEntity.ok(reviewResponses);
    }
    //user
    @GetMapping("/user/{userId}/project/{projectId}")

    public ResponseEntity<?> getUserFilter(@PathVariable Integer userId,@PathVariable Integer projectId) {
        List<UserProjectReponse> userFilter = reviewService.getUserFilter(userId,projectId);
        return ResponseEntity.ok(userFilter);
    }
    @GetMapping("/user-filter/{userId}")

    public ResponseEntity<?> getUserFilterPAU(@PathVariable Integer userId) {
        List<UserProjectReponse> userFilter = reviewService.getUserFilterPAU(userId);
        return ResponseEntity.ok(userFilter);
    }
}
