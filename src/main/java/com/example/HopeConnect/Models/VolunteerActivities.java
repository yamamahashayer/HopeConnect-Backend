package com.example.HopeConnect.Models;

import com.example.HopeConnect.Enumes.VolunteerActivityStatus;
import com.example.HopeConnect.Enumes.VolunteerActivityType;
import com.example.HopeConnect.Enumes.VolunteerAvailability;
import com.example.HopeConnect.Models.OrphanProject;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter

@Entity
@Table(name = "volunteer_activities")
public class VolunteerActivities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "volunteer_id", nullable = false)
    private Volunteer volunteer;

    @ManyToOne
    @JoinColumn(name = "orphanage_id")
    private Orphanage orphanage;


    @ManyToOne
    @JoinColumn(name = "project_id")
    private OrphanProject project;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VolunteerActivityType serviceType;

    @Column(nullable = false, length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VolunteerAvailability availability;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VolunteerActivityStatus status;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column(length = 500)
    private String notes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }


}
