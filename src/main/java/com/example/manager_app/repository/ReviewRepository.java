package com.example.manager_app.repository;

import com.example.manager_app.model.Retro;
import com.example.manager_app.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findReviewByUserReviewerId(Integer userId);
  //  List<Review>findReviewByProjectId(Integer projectId);
    List<Review>findReviewByReviewDate(LocalDate date);

    List<Review>findReviewByRetroId(Integer retroId);


}
