package com.example.manager_app.service;

import com.example.manager_app.config.ReviewMapper;
import com.example.manager_app.dto.ProjectByUserRespone;
import com.example.manager_app.dto.ReviewResponse;
import com.example.manager_app.dto.UserProjectReponse;
import com.example.manager_app.model.Retro;
import com.example.manager_app.model.Review;
import com.example.manager_app.model.Users;
import com.example.manager_app.repository.RetroRepository;
import com.example.manager_app.repository.ReviewRepository;
import com.example.manager_app.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    @Autowired
    private UserService userService;
    private final ModelMapper modelMapper;

    public ReviewService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    //admin
    public Page<ReviewResponse> getAllPage(Pageable pageable) {
        //List<Review>reviewByUsersIs=reviewRepository.findReviewByUsersId(userId);
        Page<Review> reviewList = reviewRepository.findAll(pageable);
        List<ReviewResponse> list = new ArrayList<>();
        for (Review item : reviewList) {
//            modelMapper.addMappings(new PropertyMap<Review, ReviewResponse>() {
//            //proertyMap chỉ định cách map từng field
//                @Override
//                protected void configure() {
//                    map().setNameUserReviewer(source.getUserReviewer().getUsername());
//                    map().setNameUserReviewee(source.getUserReviewee().getUsername());
//                    map().setNameRetro(source.getRetro().getName());
//                }
//            });
//            ReviewResponse reviewResponse = modelMapper.map(item, ReviewResponse.class);
            ReviewResponse reviewResponse = ReviewMapper.MAPPER.toReviewResponse(item);
            list.add(reviewResponse);
        }
        return new PageImpl<>(list, pageable, reviewList.getTotalElements());
    }

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
        List<ReviewResponse> list = new ArrayList<>();
        List<Retro> retroList = retroRepository.findRetroByProjectId(projectId);
        for (Retro item : retroList) {
            List<Review> reviewList = reviewRepository.findReviewByRetroId(item.getId());
            for (Review item1 : reviewList) {
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
    public List<ReviewResponse> getReviewByReviewerId(Integer reviewerId) {
        List<Review> reviewList = reviewRepository.findReviewByUserReviewerId(reviewerId);
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
    public ReviewResponse addReviewUser(Review review) {
        LocalDate reviewDate = LocalDate.now(); // Lấy ngày hiện tại
        review.setReviewDate(reviewDate);
        Review saveReview = reviewRepository.save(review);
        ReviewResponse reviewResponse = modelMapper.map(saveReview, ReviewResponse.class);
        Optional<Users> usersOptional = userRepository.findById(review.getUserReviewee().getId());
        reviewResponse.setNameUserReviewer(usersOptional.get().getUsername());
        Optional<Users> userOptional = userRepository.findById(review.getUserReviewee().getId());
        reviewResponse.setNameUserReviewee(userOptional.get().getUsername());
        Optional<Retro> retroOptional = retroRepository.findById(review.getRetro().getId());
        reviewResponse.setNameRetro(retroOptional.get().getName());
        return reviewResponse;
    }

    //user
    public List<ReviewResponse> getReviewUser(Integer userId) {
        List<ReviewResponse> list = new ArrayList<>();
        LocalDate reviewDate = LocalDate.now(); // Lấy ngày hiện tại
        List<ProjectByUserRespone> userProjectReponseList = projectService.getProjectByUser(userId);
        for (ProjectByUserRespone item : userProjectReponseList) {
            List<Retro> retroList = retroService.getRetroByProjectId(item.getId());
            for (Retro item1 : retroList) {
                List<Review> reviewList = reviewRepository.findReviewByRetroId(item1.getId());
                for (Review item2 : reviewList) {
                    List<ProjectByUserRespone> userProjectReponseList1 = projectService.getProjectByUser(item2.getUserReviewee().getId());
                    for (ProjectByUserRespone item3 : userProjectReponseList1) {
                        if (item.getId() == item3.getId()) {
                            if (item2.getUserReviewer().getId() == userId) {
                                ReviewResponse reviewResponse = modelMapper.map(item2, ReviewResponse.class);
                                reviewResponse.setNameUserReviewer(item.getUserName());
                                reviewResponse.setNameUserReviewee(item2.getUserReviewee().getUsername());
                                reviewResponse.setNameRetro(item1.getName());
                                list.add(reviewResponse);

                            }
                        }
                    }
                }
            }
        }
        return list;
    }
    //user : lọc ra các user có cùng project và cùng 1 user(login) đánh giá nhưng chưa được user(login đánh giá)

    public List<UserProjectReponse> getUserFilter(Integer userId, Integer projectId) {
        List<UserProjectReponse> list1 = new ArrayList<>();
        List<UserProjectReponse> list2 = new ArrayList<>();
        List<Review> reviewList = reviewRepository.findReviewByUserReviewerId(userId);
        List<UserProjectReponse> userProjectReponseList = userService.getUserByProject(projectId);
        for (Review item : reviewList) {
            for (UserProjectReponse item1 : userProjectReponseList) {
                if (item1.getId() == item.getUserReviewee().getId()) {
                    list1.add(item1);
                }

            }

        }
        for (UserProjectReponse item : list1) {
            if (!userProjectReponseList.contains(item)) {
                list2.add(item);
            }
        }
        for (UserProjectReponse item1 : userProjectReponseList) {
            if (!list1.contains(item1)) {
                list2.add(item1);
            }
        }
        return list2;
    }
    //user : lọc ra các user có cùng project và cùng 1 user(login)
    public List<UserProjectReponse> getUserFilterPAU(Integer userId) {
        List<UserProjectReponse> list = new ArrayList<>();
        List<ProjectByUserRespone> projectByUserResponeList = projectService.getProjectByUser(userId);
            for (ProjectByUserRespone item1 : projectByUserResponeList) {
                List<UserProjectReponse>userProjectReponseList=userService.getUserByProject(item1.getId());
                for (UserProjectReponse item:userProjectReponseList) {
                    list.add(item);
                }
            }

        return list;
    }
}
