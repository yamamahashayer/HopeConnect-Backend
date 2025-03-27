package com.example.HopeConnect.Models.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "volunteer")
public class Volunteer extends User {

   @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   @MapsId
   @JoinColumn(name = "id")
   private User user;

   @Column(nullable = false, columnDefinition = "TEXT")
   private String skills;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private Availability availability;

   @Column(name = "experience_years")
   private int experienceYears = 0;

   @Column(name = "preferred_activities", columnDefinition = "TEXT")
   private String preferredActivities;

   @Column(length = 100)
   private String location;

   @Enumerated(EnumType.STRING)
   private Status status = Status.PENDING;

   @Column(name = "registered_at", updatable = false)
   private LocalDateTime registeredAt;

   @PrePersist
   protected void onCreate() {
      this.registeredAt = LocalDateTime.now();
   }

   public enum Availability {
      FULL_TIME, PART_TIME, FLEXIBLE
   }

   public enum Status {
      ACTIVE, INACTIVE, PENDING
   }

   // Getters and Setters
   public User getUser() { return user; }
   public void setUser(User user) { this.user = user; }

   public String getSkills() { return skills; }
   public void setSkills(String skills) { this.skills = skills; }

   public Availability getAvailability() { return availability; }
   public void setAvailability(Availability availability) { this.availability = availability; }

   public int getExperienceYears() { return experienceYears; }
   public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }

   public String getPreferredActivities() { return preferredActivities; }
   public void setPreferredActivities(String preferredActivities) { this.preferredActivities = preferredActivities; }

   public String getLocation() { return location; }
   public void setLocation(String location) { this.location = location; }

   public Status getStatus() { return status; }
   public void setStatus(Status status) { this.status = status; }

   public LocalDateTime getRegisteredAt() { return registeredAt; }
}
