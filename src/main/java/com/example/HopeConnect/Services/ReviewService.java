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
    private OrphanageService orphanageService;


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
        if (reviewDTO.getOrphanageId() != null) {
            switch (reviewer.getUserType()) {
                case VOLUNTEER:
                    validateVolunteerReview(reviewer, reviewDTO.getOrphanageId());
                    break;
                case SPONSOR:
                    validateSponsorReview(reviewer, reviewDTO.getOrphanageId());
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
        System.out.println("Requested orphanage ID: " + orphanageId);

        boolean hasDonated = donationService.getDonationsByUserId(reviewer.getId()).stream()
                .anyMatch(donation -> {
                    System.out.println("Checking donation: " + donation.getId());

                    if (donation.getOrphanage() != null) {
                        System.out.println("Direct orphanage ID: " + donation.getOrphanage().getId());
                        if (donation.getOrphanage().getId().equals(orphanageId)) {
                            return true;
                        }
                    }

                    if (donation.getOrphan() != null) {
                        Orphan orphan = donation.getOrphan();
                        System.out.println("Donation linked to orphan ID: " + orphan.getId());
                        if (orphan.getOrphanage() != null) {
                            System.out.println("Indirect orphanage ID: " + orphan.getOrphanage().getId());
                            return orphan.getOrphanage().getId().equals(orphanageId);
                        } else {
                            System.out.println("Orphan has no orphanage assigned.");
                        }
                    }

                    return false;
                });

        if (!hasDonated) {
            throw new IllegalArgumentException("You must have donated to this orphanage to leave a review.");
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

        if (reviewDTO.getOrphanageId() != null) {
            Orphanage orphanage = new Orphanage();
            orphanage.setId(reviewDTO.getOrphanageId());
            review.setOrphanage(orphanage);
        }
        if (reviewDTO.getOrphanId() != null) {
            Orphan orphan = new Orphan();
            orphan.setId(reviewDTO.getOrphanId());
            review.setOrphan(orphan);
        }
        if (reviewDTO.getProjectId() != null) {
            OrphanProject project = new OrphanProject();
            project.setId(reviewDTO.getProjectId());
            review.setProject(project);
        }

     

        // Validate that the orphanage exists before saving
        String orphanageName = getOrphanageNameById(reviewDTO.getOrphanageId());
        if (orphanageName.equals("Unknown Orphanage")) {
            throw new IllegalArgumentException("Orphanage with ID " + reviewDTO.getOrphanageId() + " does not exist.");
        }


        return reviewRepository.save(review);
    }

    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setReviewerId(review.getReviewer().getId());

        dto.setOrphanageId(review.getOrphanageId());

        dto.setReviewDate(review.getReviewDate().toString());

        dto.setReviewerName(review.getReviewer().getName());
        dto.setReviewerType(review.getReviewer().getUserType().name());
        dto.setReviewerEmail(review.getReviewer().getEmail());

        if (review.getOrphanage() != null) {
            dto.setOrphanageId(review.getOrphanage().getId());
        }
        if (review.getOrphan() != null) {

        String orphanageName = getOrphanageNameById(review.getOrphanageId());
        dto.setOrphanageName(orphanageName);

        }
        if (review.getProject() != null) {
            dto.setProjectId(review.getProject().getId());
        }

        return dto;
    }


    private String getOrphanageNameById(Long orphanageId) {
        return orphanageService.findById(orphanageId)
                .map(orphanage -> orphanage.getName())
                .orElse("Unknown Orphanage");
    }

}

