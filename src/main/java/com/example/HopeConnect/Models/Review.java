package com.example.HopeConnect.Models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;
    private int rating;


    private LocalDate reviewDate;


    @ManyToOne
    private User reviewer;

    @ManyToOne
    @JoinColumn(name = "orphanage_id")
    private Orphanage orphanage;


    @ManyToOne
    @JoinColumn(name = "orphan_id")
    private Orphan orphan;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private OrphanProject project;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    private Long orphanId;
    private Long projectId;

    private int rating;
    private String comment;
    private LocalDate reviewDate;
    private long orphanageId;

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


    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public LocalDate getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDate reviewDate) { this.reviewDate = reviewDate; }

    public User getReviewer() { return reviewer; }
    public void setReviewer(User reviewer) { this.reviewer = reviewer; }

    public Orphanage getOrphanage() { return orphanage; }
    public void setOrphanage(Orphanage orphanage) { this.orphanage = orphanage; }
    public Orphan getOrphan() { return orphan; }
    public void setOrphan(Orphan orphan) { this.orphan = orphan; }

    }

    public long getOrphanageId() {
        return orphanageId;
    }

    public void setOrphanageId (Long orphanageId){
        this.orphanageId = orphanageId;


    public OrphanProject getProject() { return project; }
    public void setProject(OrphanProject project) { this.project = project; }
}
