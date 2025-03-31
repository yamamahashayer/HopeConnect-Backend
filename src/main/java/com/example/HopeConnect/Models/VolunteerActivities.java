package com.example.HopeConnect.Models;

import com.example.HopeConnect.Enumes.VolunteerActivityStatus;
import com.example.HopeConnect.Enumes.VolunteerActivityType;
import com.example.HopeConnect.Enumes.VolunteerAvailability;
import jakarta.persistence.*;
import java.time.LocalDateTime;

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


    //    @ManyToOne
    //    @JoinColumn(name = "project_id")
    //    private OrphanageProject project;


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

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Volunteer getVolunteer() { return volunteer; }
    public void setVolunteer(Volunteer volunteer) { this.volunteer = volunteer; }

    public Orphanage getOrphanage() { return orphanage; }
    public void setOrphanage(Orphanage orphanage) { this.orphanage = orphanage; }

    public VolunteerActivityType getServiceType() { return serviceType; }
    public void setServiceType(VolunteerActivityType serviceType) { this.serviceType = serviceType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public VolunteerAvailability getAvailability() { return availability; }
    public void setAvailability(VolunteerAvailability availability) { this.availability = availability; }

    public VolunteerActivityStatus getStatus() { return status; }
    public void setStatus(VolunteerActivityStatus status) { this.status = status; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
