package com.example.HopeConnect.Models;

import com.example.HopeConnect.Enumes.Gender;
import com.example.HopeConnect.Enumes.OrphanStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Entity
@Table(name = "orphans")
public class Orphan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "health_status")
    private String healthStatus;

    @Column(name = "education_status")
    private String educationStatus;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrphanStatus status = OrphanStatus.AVAILABLE;

    @ManyToOne
    @JsonBackReference("orphanage-orphans")
    @JoinColumn(name = "orphanage_id", nullable = false)
    private Orphanage orphanage;

    @ManyToOne
    @JsonBackReference("sponsor-orphans")
    @JoinColumn(name = "sponsor_id")
    private Sponsor sponsor;

    @ManyToOne
    @JsonBackReference("project-orphans")
    @JoinColumn(name = "project_id")
    private OrphanProject orphanProject;


    //diala
    @OneToMany(mappedBy = "orphan")
    @JsonManagedReference("donation-orphan")
    private List<Donation> donations;

    //diala
    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt = LocalDate.now();

    @Column(name = "orphanage_id", insertable = false, updatable = false)
    private Long orphanageId;

    public int getAge() {
        return (dateOfBirth != null) ? Period.between(dateOfBirth, LocalDate.now()).getYears() : 0;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public String getHealthStatus() { return healthStatus; }
    public void setHealthStatus(String healthStatus) { this.healthStatus = healthStatus; }

    public String getEducationStatus() { return educationStatus; }
    public void setEducationStatus(String educationStatus) { this.educationStatus = educationStatus; }

    public String getProfileImage() { return profileImage; }
    public void setProfileImage(String profileImage) { this.profileImage = profileImage; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public OrphanStatus getStatus() { return status; }
    public void setStatus(OrphanStatus status) { this.status = status; }

    public Orphanage getOrphanage() { return orphanage; }
    public void setOrphanage(Orphanage orphanage) { this.orphanage = orphanage; }

    public Sponsor getSponsor() { return sponsor; }
    public void setSponsor(Sponsor sponsor) { this.sponsor = sponsor; }

    public OrphanProject getOrphanProject() { return orphanProject; }
    public void setOrphanProject(OrphanProject orphanProject) { this.orphanProject = orphanProject; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

    public Long getOrphanageId() { return orphanageId; }
}
