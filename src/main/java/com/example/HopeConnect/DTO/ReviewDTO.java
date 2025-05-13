package com.example.HopeConnect.DTO;

import java.time.LocalDate;
public class ReviewDTO {
    private Long id;
    private String comment;
    private int rating;
    private String reviewDate;
    private Long reviewerId;
    private String reviewerName;
    private String reviewerType;
    private String reviewerEmail;
     private Long orphanageId;
   //private Long targetId;
    private String targetType; // "ORPHANAGE", "DONATION", ...

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
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

    public String getReviewerEmail() {
        return reviewerEmail;
    }

    public void setReviewerEmail(String reviewerEmail) {
        this.reviewerEmail = reviewerEmail;
    }

    public Long getOrphanageId() {
        return orphanageId;
    }

    public void setOrphanageId(Long orphanageId) {
        this.orphanageId = orphanageId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
//    public Long getTargetId() {
//        return targetId;
//    }
//
//    public void setTargetId(Long targetId) {
//        this.targetId = targetId;
//    }

}