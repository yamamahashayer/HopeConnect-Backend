package com.example.HopeConnect.Models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.example.HopeConnect.Enumes.VolunteerStatus;
import com.example.HopeConnect.Enumes.VolunteerAvailability;
@Entity
public class Volunteer {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @OneToOne
   @JoinColumn(name = "user_id", nullable = false, unique = true)
   private User user;

   @Column(nullable = false, columnDefinition = "TEXT")
   private String skills;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false)
   private VolunteerAvailability availability;

   @Column(name = "experience_years")
   private int experienceYears = 0;

   @Column(name = "preferred_activities", columnDefinition = "TEXT")
   private String preferredActivities;

   @Column(length = 100)
   private String location;

   @Enumerated(EnumType.STRING)
   private VolunteerStatus status = VolunteerStatus.PENDING;

   @Column(name = "registered_at", updatable = false)
   private LocalDateTime registeredAt;

   @PrePersist
   protected void onCreate() {
      this.registeredAt = LocalDateTime.now();
   }

   // Getters and Setters
   public Long getId() { return id; }
   public void setId(Long id) { this.id = id; }

   public User getUser() { return user; }
   public void setUser(User user) { this.user = user; }

   public String getSkills() { return skills; }
   public void setSkills(String skills) { this.skills = skills; }

   public VolunteerAvailability getAvailability() { return availability; }
   public void setAvailability(VolunteerAvailability availability) { this.availability = availability; }

   public int getExperienceYears() { return experienceYears; }
   public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }

   public String getPreferredActivities() { return preferredActivities; }
   public void setPreferredActivities(String preferredActivities) { this.preferredActivities = preferredActivities; }

   public String getLocation() { return location; }
   public void setLocation(String location) { this.location = location; }

   public VolunteerStatus getStatus() { return status; }
   public void setStatus(VolunteerStatus status) { this.status = status; }

   public LocalDateTime getRegisteredAt() {
      return registeredAt;
   }

   public void setRegisteredAt(LocalDateTime registeredAt) {
      this.registeredAt = registeredAt;
   }

}
