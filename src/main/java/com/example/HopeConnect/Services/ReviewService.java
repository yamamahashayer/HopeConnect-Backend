package com.example.HopeConnect.Services;

import com.example.HopeConnect.DTO.ReviewDTO;
import com.example.HopeConnect.Models.Review;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Repositories.ReviewRepository;
import com.example.HopeConnect.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;



        public ReviewDTO createReview(ReviewDTO reviewDTO) {
            // ✅ البحث عن المستخدم بناءً على ID
            User reviewer = userRepository.findById(reviewDTO.getReviewerId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            // ✅ التحقق من صلاحيات المستخدم بناءً على نوعه
            switch (reviewer.getUserType()) {
                case VOLUNTEER:
                    if (!hasVolunteered(reviewer.getId(), reviewDTO.getOrphanageId())) {
                        throw new IllegalArgumentException("You must have volunteered in this orphanage to leave a review.");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Only volunteers can leave reviews for orphanages.");
            }

            // ✅ إنشاء الـ Review بعد التحقق
            Review review = new Review();
            review.setRating(reviewDTO.getRating());
            review.setComment(reviewDTO.getComment());
            review.setReviewer(reviewer);
            review.setTargetId(reviewDTO.getOrphanageId());
            review.setReviewDate(LocalDate.now());

            // ✅ حفظ المراجعة
            Review savedReview = reviewRepository.save(review);
            return convertToDTO(savedReview);
        }


    private boolean hasVolunteered(Long userId, Long orphanageId) {
        String sql = "SELECT COUNT(*) " +
                "FROM volunteer_activities va " +
                "WHERE va.orphanage_id = ? AND va.volunteer_id = ?";

        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, orphanageId, userId);
            return count != null && count > 0;
        } catch (Exception e) {
            System.err.println("Error in hasVolunteered method: " + e.getMessage());
            return false;
        }
    }



    // ✅ تحويل الـ Review إلى DTO
        private ReviewDTO convertToDTO(Review review) {
            ReviewDTO dto = new ReviewDTO();
            dto.setId(review.getId());
            dto.setRating(review.getRating());
            dto.setComment(review.getComment());
            dto.setReviewerId(review.getReviewer().getId());
            dto.setOrphanageId(review.getTargetId());
            dto.setReviewDate(review.getReviewDate());
            return dto;
        }
    }


