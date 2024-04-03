package com.example.manager_app.service;

import com.example.manager_app.dto.ProjectByUserRespone;
import com.example.manager_app.dto.ReviewRequest;
import com.example.manager_app.dto.ReviewResponse;
import com.example.manager_app.dto.UserProjectReponse;
import com.example.manager_app.model.Project;
import com.example.manager_app.model.Retro;
import com.example.manager_app.model.Review;
import com.example.manager_app.model.Users;
import com.example.manager_app.repository.RetroRepository;
import com.example.manager_app.repository.ReviewRepository;
import com.example.manager_app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private RetroService retroService;
    @Autowired
    private RetroRepository retroRepository;
    private final ModelMapper modelMapper;

    public ReviewService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //admin
    public List<ReviewResponse> getAll() {
        //List<Review>reviewByUsersIs=reviewRepository.findReviewByUsersId(userId);
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

    //admin
    public List<ReviewResponse> getReviewByProjectId(Integer projectId) {
        List<ReviewResponse>list=new ArrayList<>();
        List<Retro> retroList = retroRepository.findRetroByProjectId(projectId);
        for (Retro item:retroList) {
            List<Review>reviewList=reviewRepository.findReviewByRetroId(item.getId());
            for (Review item1:reviewList) {
                ReviewResponse reviewResponse = modelMapper.map(item1, ReviewResponse.class);
                reviewResponse.setNameUserReviewer(item1.getUserReviewer().getUsername());
                reviewResponse.setNameUserReviewee(item1.getUserReviewee().getUsername());
                reviewResponse.setNameRetro(item1.getRetro().getName());
                list.add(reviewResponse);
            }
        }
        return list;
    }

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
//user
    public ReviewResponse addReviewUser(ReviewRequest review) {
        LocalDate reviewDate = LocalDate.now(); // Lấy ngày hiện tại
        List<ProjectByUserRespone> userProjectReponseList = projectService.getProjectByUser(review.getReviewerId().getId());
        for (ProjectByUserRespone item : userProjectReponseList) {
            List<Retro> retroList = retroService.getRetroByProjectIdAndDate(item.getId());
            for (Retro item1 : retroList) {
//                List<Review> reviewList = reviewRepository.findReviewByRetroId(review.getRetroId());
//                for (Review item2 : reviewList) {
                        if (review.getRevieweeId() != review.getReviewerId()){
                            Review review1 = new Review();
                            review1.setReviewDate(reviewDate);
                            review1.setUserReviewer(review.getReviewerId());
                            review1.setUserReviewee(review.getRevieweeId());
                            review1.setRetro(item1);
                            review1.setRate(review.getRate());
                            review1.setComment(review.getComment());
                            Review saveReview=reviewRepository.save(review1);
                            ReviewResponse reviewResponse = modelMapper.map(saveReview, ReviewResponse.class);
                            reviewResponse.setNameUserReviewer(item.getUserName());
                            Optional<Users>usersOptional=userRepository.findById(review.getRevieweeId().getId());
                            Users users=usersOptional.get();
                            reviewResponse.setNameUserReviewee(users.getUsername());
                            reviewResponse.setNameRetro(item1.getName());
                            return reviewResponse;
                        }
                    }
                }
//            }
       return null;
    }
}
