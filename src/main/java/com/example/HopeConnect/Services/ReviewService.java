package com.example.HopeConnect.Services;

import com.example.HopeConnect.DTO.ReviewDTO;
import com.example.HopeConnect.Models.Review;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Models.Volunteer;
import com.example.HopeConnect.Repositories.ReviewRepository;
import com.example.HopeConnect.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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

    @Autowired
    private SponsorActivitiesService sponsorActivitiesService;

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

        // تطبق فقط تحقق orphanage إذا هو المستهدف
        if (reviewDTO.getOrphanageId() != null) {
            switch (reviewer.getUserType()) {
                case VOLUNTEER:
                    validateVolunteerReview(reviewer, reviewDTO.getOrphanageId());
                    break;
                case SPONSOR:
                    validateSponsorReview(reviewer, reviewDTO.getOrphanageId());
                    break;
                case DONOR:
                    break;
                case ADMIN:
                case ORPHANAGE_MANAGER:
                    throw new IllegalArgumentException("Admins and Orphanage Managers cannot leave reviews.");
                default:
                    throw new IllegalArgumentException("Invalid user type.");
            }
        }

        Review review = createAndSaveReview(reviewDTO, reviewer);
        return convertToDTO(review);
    }

    private void validateReviewDTO(ReviewDTO reviewDTO) {
        if (reviewDTO.getReviewerEmail() == null || reviewDTO.getReviewerEmail().isEmpty()) {
            throw new IllegalArgumentException("Reviewer email must not be null or empty.");
        }

        // يجب أن يحدد هدف واحد فقط
        int targetsSpecified = 0;
        if (reviewDTO.getOrphanageId() != null) targetsSpecified++;
        if (reviewDTO.getOrphanId() != null) targetsSpecified++;
        if (reviewDTO.getProjectId() != null) targetsSpecified++;

        if (targetsSpecified == 0) {
            throw new IllegalArgumentException("You must specify one target to review (orphanageId, orphanId, or projectId).");
        } else if (targetsSpecified > 1) {
            throw new IllegalArgumentException("You can only review one target at a time.");
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

    private void validateSponsorReview(User reviewer, Long orphanageId) {
        boolean hasActivity = sponsorActivitiesService.getActivitiesBySponsorId(reviewer.getId()).stream()
                .anyMatch(activity -> activity.getOrphanage().getId().equals(orphanageId));

        if (!hasActivity) {
            throw new IllegalArgumentException("You must have supported this orphanage to leave a review.");
        }
    }

    private Review createAndSaveReview(ReviewDTO reviewDTO, User reviewer) {
        Review review = new Review();
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setReviewer(reviewer);
        review.setReviewDate(LocalDate.now());

        // حفظ الهدف الصحيح فقط
        if (reviewDTO.getOrphanageId() != null) {
            review.setTargetId(reviewDTO.getOrphanageId());
        }
        if (reviewDTO.getOrphanId() != null) {
            review.setOrphanId(reviewDTO.getOrphanId());
        }
        if (reviewDTO.getProjectId() != null) {
            review.setProjectId(reviewDTO.getProjectId());
        }

        return reviewRepository.save(review);
    }

    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setReviewerId(review.getReviewer().getId());
        dto.setReviewDate(review.getReviewDate().toString());
        dto.setReviewerName(review.getReviewer().getName());
        dto.setReviewerType(review.getReviewer().getUserType().name());
        dto.setReviewerEmail(review.getReviewer().getEmail());

        if (review.getTargetId() != null) {
            dto.setOrphanageId(review.getTargetId());
        }
        if (review.getOrphanId() != null) {
            dto.setOrphanId(review.getOrphanId());
        }
        if (review.getProjectId() != null) {
            dto.setProjectId(review.getProjectId());
        }

        return dto;
    }
}
