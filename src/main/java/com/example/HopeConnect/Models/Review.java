package com.example.HopeConnect.Models;

import jakarta.persistence.*;

import java.time.LocalDate;
// Review.java
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    private User reviewer;
    private int rating;
    private String comment;
    private LocalDate reviewDate;
    private  long orphanageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }


    public long getOrphanageId() {
        return orphanageId;
    }

    public void setOrphanageId(Long orphanageId) {
        this.orphanageId = orphanageId;
    }
}
