package com.example.manager_app.config;

import com.example.manager_app.dto.ReviewResponse;
import com.example.manager_app.model.Retro;
import com.example.manager_app.model.Review;
import com.example.manager_app.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
@Mapper
public interface ReviewMapper {
    ReviewMapper MAPPER = Mappers.getMapper(ReviewMapper.class);
//source : đối tượng nguồn cần chuyển đổi
//target : đối tượng đích sau khi chuyển đổi
//qualifiedByName : chuyển đối kiểu dữ liệu
    @Mapping(source = "userReviewer", target = "nameUserReviewer", qualifiedByName = "userToString")
    @Mapping(source = "retro", target = "nameRetro", qualifiedByName = "retroToString")
    @Mapping(source = "userReviewee", target = "nameUserReviewee", qualifiedByName = "userToString")
    ReviewResponse toReviewResponse(Review review);

    @Named("userToString") //định nghĩa tên cho phương thức chuyển đổi
    default String userToString(Users user) {
        return user != null ? user.getUsername() : null;
    }

    @Named("retroToString")
    default String retroToString(Retro retro) {
        return retro != null ? retro.getName() : null;
    }
}
