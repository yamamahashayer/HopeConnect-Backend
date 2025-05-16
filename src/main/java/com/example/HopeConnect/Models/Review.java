package com.example.HopeConnect.Models;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @ManyToOne
    private User reviewer;


    private Long orphanId; // ID of orphan (optional)
    private Long projectId; // ID of project (optional)

    private int rating;
    private String comment;
    private LocalDate reviewDate;
    private  long orphanageId;

    // Getters and Setters
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


    public Long getOrphanId() {
        return orphanId;
    }

    public void setOrphanId(Long orphanId) {
        this.orphanId = orphanId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;

    public void setOrphanageId(Long orphanageId) {
        this.orphanageId = orphanageId;

    }
}
