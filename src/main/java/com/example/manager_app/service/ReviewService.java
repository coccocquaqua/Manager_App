package com.example.manager_app.service;

import com.example.manager_app.dto.ReviewResponse;
import com.example.manager_app.model.Review;
import com.example.manager_app.repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public ReviewService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //admin
    public List<ReviewResponse> getAll() {
        //List<Review>reviewByUsersIs=reviewRepository.findReviewByUsersIs(userId);
        List<Review> reviewList = reviewRepository.findAll();
        List<ReviewResponse> list = new ArrayList<>();
        for (Review item : reviewList) {
            ReviewResponse reviewResponse = modelMapper.map(item, ReviewResponse.class);
            reviewResponse.setNameUserReviewer(item.getUserReviewer().getUsername());
            reviewResponse.setNameUserReviewee(item.getUserReviewee().getUsername());
            reviewResponse.setNameRetro(item.getRetro().getName());
            list.add(reviewResponse);
        }
        return list;
    }

    //    public ReviewResponse addReview(Review review){
//
//
//    }
    //admin
//    public List<ReviewResponse> getReviewByProjectId(Integer projectId) {
//        List<Review> reviewList = reviewRepository.findReviewByProjectId(projectId);
//        List<ReviewResponse> list = new ArrayList<>();
//        for (Review item : reviewList) {
//            ReviewResponse reviewResponse = modelMapper.map(item, ReviewResponse.class);
//            reviewResponse.setNameUserReviewer(item.getUserReviewer().getUsername());
//            reviewResponse.setNameUserReviewee(item.getUserReviewee().getUsername());
//            reviewResponse.setNameRetro(item.getRetro().getName());
//            list.add(reviewResponse);
//        }
//        return list;
//    }

    //admin
    public List<ReviewResponse> getReviewByUserId(Integer userId) {
        List<Review> reviewList = reviewRepository.findReviewByUserReviewerId(userId);
        List<ReviewResponse> list = new ArrayList<>();
        for (Review item : reviewList) {
            ReviewResponse reviewResponse = modelMapper.map(item, ReviewResponse.class);
            reviewResponse.setNameUserReviewer(item.getUserReviewer().getUsername());
            reviewResponse.setNameUserReviewee(item.getUserReviewee().getUsername());
            reviewResponse.setNameRetro(item.getRetro().getName());
            list.add(reviewResponse);
        }
        return list;
    }

    //admin
    public List<ReviewResponse> getReviewByReviewDate(LocalDate date) {
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Review> reviewList = reviewRepository.findReviewByReviewDate(date);
        List<ReviewResponse> list = new ArrayList<>();
        for (Review item : reviewList) {
            ReviewResponse reviewResponse = modelMapper.map(item, ReviewResponse.class);
            reviewResponse.setNameUserReviewer(item.getUserReviewer().getUsername());
            reviewResponse.setNameUserReviewee(item.getUserReviewee().getUsername());
            reviewResponse.setNameRetro(item.getRetro().getName());
            list.add(reviewResponse);
        }
        return list;
    }

}
