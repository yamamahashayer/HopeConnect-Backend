package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTargetId(Long orphanageId); // ✅ البحث بناءً على ID دار الأيتام
}
