package com.example.HopeConnect.Services;

import com.example.HopeConnect.DTO.ReviewDTO;
import com.example.HopeConnect.Models.Review;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Models.Volunteer;
import com.example.HopeConnect.Repositories.ReviewRepository;
import com.example.HopeConnect.Repositories.UserRepository;
import com.example.HopeConnect.Services.UserServices;
import com.example.HopeConnect.Services.VolunteerService;
import com.example.HopeConnect.Services.VolunteerActivitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private VolunteerActivitiesService volunteerActivitiesService;


    @Autowired
    private UserRepository userRepository;


    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public void deleteReviewById(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new IllegalArgumentException("Review with ID " + id + " does not exist.");
        }
        reviewRepository.deleteById(id);
    }
    public List<ReviewDTO> getUserReviewsByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User with this email not found."));

        List<Review> userReviews = reviewRepository.findByReviewer(user);
        return userReviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        validateReviewDTO(reviewDTO);

        User reviewer = userServices.findByEmail(reviewDTO.getReviewerEmail())
                .orElseThrow(() -> new IllegalArgumentException("User with this email not found."));

        switch (reviewer.getUserType()) {
            case VOLUNTEER -> validateVolunteerReview(reviewer, reviewDTO.getOrphanageId());
            case SPONSOR, DONOR -> {
            }
            case ADMIN, ORPHANAGE_MANAGER -> throw new IllegalArgumentException("Admins and Orphanage Managers cannot leave reviews.");
            default -> throw new IllegalArgumentException("Invalid user type.");
        }

        Review review = createAndSaveReview(reviewDTO, reviewer);
        return convertToDTO(review);
    }

    private void validateReviewDTO(ReviewDTO reviewDTO) {
        if (reviewDTO.getReviewerEmail() == null || reviewDTO.getReviewerEmail().isEmpty()) {
            throw new IllegalArgumentException("Reviewer email must not be null or empty.");
        }
        if (reviewDTO.getOrphanageId() == null) {
            throw new IllegalArgumentException("Orphanage ID must not be null.");
        }
    }

    private void validateVolunteerReview(User reviewer, Long orphanageId) {
        Volunteer volunteer = volunteerService.getVolunteerByUserId(reviewer.getId())
                .orElseThrow(() -> new IllegalArgumentException("You are registered as a volunteer, but no volunteer profile was found."));

        boolean hasActivity = volunteerActivitiesService.getActivitiesByVolunteerId(volunteer.getId()).stream()
                .anyMatch(activity -> activity.getOrphanageId().equals(orphanageId));

        if (!hasActivity) {
            throw new IllegalArgumentException("You must have volunteered in this orphanage to leave a review.");
        }
    }

    private Review createAndSaveReview(ReviewDTO reviewDTO, User reviewer) {
        Review review = new Review();
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setReviewer(reviewer);
        review.setTargetId(reviewDTO.getOrphanageId());
        review.setReviewDate(LocalDate.now());

        return reviewRepository.save(review);
    }

    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setReviewerId(review.getReviewer().getId());
        dto.setOrphanageId(review.getTargetId());
        dto.setReviewDate(review.getReviewDate().toString());

        dto.setReviewerName(review.getReviewer().getName());
        dto.setReviewerType(review.getReviewer().getUserType().name());
        dto.setReviewerEmail(review.getReviewer().getEmail());

        return dto;
    }

}
