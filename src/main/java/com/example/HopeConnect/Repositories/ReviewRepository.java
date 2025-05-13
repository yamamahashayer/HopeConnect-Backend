package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Enumes.ReviewTargetType;
import com.example.HopeConnect.Models.Review;
import com.example.HopeConnect.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByReviewer(User reviewer);
//diala
    List<Review> findByTargetIdAndTargetType(Long targetId, ReviewTargetType targetType);
//
}
