package com.example.HopeConnect.DTO;

import java.time.LocalDate;

public class ReviewDTO {
    private Long id;
    private String reviewerName;
    private String reviewerType;
    private String comment;
    private int rating;
    private LocalDate reviewDate;
    private Long reviewerId;
    private Long orphanageId;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewerType() {
        return reviewerType;
    }

    public void setReviewerType(String reviewerType) {
        this.reviewerType = reviewerType;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Long getOrphanageId() {
        return orphanageId;
    }

    public void setOrphanageId(Long orphanageId) {
        this.orphanageId = orphanageId;
    }
}
