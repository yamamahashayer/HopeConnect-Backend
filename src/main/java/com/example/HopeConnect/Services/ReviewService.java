package com.example.HopeConnect.Services;

import com.example.HopeConnect.DTO.ReviewDTO;
import com.example.HopeConnect.Models.*;
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
    private DonationService donationService;

    @Autowired
    private OrphanageService orphanageService;

    @Autowired
    private OrphanService orphanService;          // <-- أضفت هذا
    @Autowired
    private OrphanProjectService projectService;  // <-- أضفت هذا

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

        switch (reviewer.getUserType()) {
            case VOLUNTEER:
                validateVolunteerReview(reviewer, reviewDTO.getOrphanageId());
                break;
            case SPONSOR:
                validateSponsorReview(
                        reviewer,
                        reviewDTO.getOrphanageId(),
                        reviewDTO.getOrphanId(),
                        reviewDTO.getProjectId()
                );
                break;
            case DONOR:
                validateDonorReview(reviewer, reviewDTO.getOrphanageId());
                break;
            case ADMIN:
            case ORPHANAGE_MANAGER:
                throw new IllegalArgumentException("Admins and Orphanage Managers cannot leave reviews.");
            default:
                throw new IllegalArgumentException("Invalid user type.");
        }

        Review review = createAndSaveReview(reviewDTO, reviewer);
        return convertToDTO(review);
    }

    private void validateReviewDTO(ReviewDTO reviewDTO) {
        if (reviewDTO.getReviewerEmail() == null || reviewDTO.getReviewerEmail().isEmpty()) {
            throw new IllegalArgumentException("Reviewer email must not be null or empty.");
        }

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

    private void validateDonorReview(User reviewer, Long orphanageId) {
        boolean hasDonated = donationService.getDonationsByUserId(reviewer.getId()).stream()
                .anyMatch(donation ->
                        (donation.getOrphanage() != null && donation.getOrphanage().getId().equals(orphanageId)) ||
                                (donation.getOrphan() != null &&
                                        donation.getOrphan().getOrphanage() != null &&
                                        donation.getOrphan().getOrphanage().getId().equals(orphanageId))
                );

        if (!hasDonated) {
            throw new IllegalArgumentException("You must have donated to this orphanage to leave a review.");
        }
    }

    private void validateSponsorReview(User reviewer, Long orphanageId, Long orphanId, Long projectId) {
        List<SponsorActivity> activities = sponsorActivitiesService.getActivitiesBySponsorId(reviewer.getId());

        boolean hasValidActivity = activities.stream().anyMatch(activity ->
                (orphanageId != null && activity.getOrphanage() != null && activity.getOrphanage().getId().equals(orphanageId)) ||
                        (orphanId != null && activity.getOrphan() != null && activity.getOrphan().getId().equals(orphanId)) ||
                        (projectId != null && activity.getProject() != null && activity.getProject().getId().equals(projectId))
        );

        if (!hasValidActivity) {
            throw new IllegalArgumentException("You must have supported this orphanage, orphan, or project to leave a review.");
        }
    }

    private Review createAndSaveReview(ReviewDTO reviewDTO, User reviewer) {
        Review review = new Review();
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        review.setReviewer(reviewer);
        review.setReviewDate(LocalDate.now());

        if (reviewDTO.getOrphanageId() != null) {
            Orphanage orphanage = orphanageService.findById(reviewDTO.getOrphanageId())
                    .orElseThrow(() -> new IllegalArgumentException("Orphanage with ID " + reviewDTO.getOrphanageId() + " does not exist."));
            review.setOrphanage(orphanage);
        }
        if (reviewDTO.getOrphanId() != null) {
            Orphan orphan = orphanService.findById(reviewDTO.getOrphanId())
                    .orElseThrow(() -> new IllegalArgumentException("Orphan with ID " + reviewDTO.getOrphanId() + " does not exist."));
            review.setOrphan(orphan);
        }
        if (reviewDTO.getProjectId() != null) {
            OrphanProject project = projectService.findById(reviewDTO.getProjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Project with ID " + reviewDTO.getProjectId() + " does not exist."));
            review.setProject(project);
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

        if (review.getOrphanage() != null) {
            dto.setOrphanageId(review.getOrphanage().getId());
            dto.setOrphanageName(review.getOrphanage().getName());
        }

        if (review.getOrphan() != null) {
            dto.setOrphanId(review.getOrphan().getId());
            if (review.getOrphan().getOrphanage() != null) {
                dto.setOrphanageName(review.getOrphan().getOrphanage().getName());
            }
        }

        if (review.getProject() != null) {
            dto.setProjectId(review.getProject().getId());

        }

        return dto;
    }

}