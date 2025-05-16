package com.example.HopeConnect.DTO;

import com.example.HopeConnect.Enumes.VolunteerAvailability;
import com.example.HopeConnect.Enumes.VolunteerStatus;
import com.example.HopeConnect.Models.User;

public class VolunteerRegistrationDTO {

    // كائن المستخدم الكامل
    private User user;

    // خصائص المتطوع فقط
    private String skills;
    private VolunteerAvailability availability;
    private int experienceYears;
    private String preferredActivities;
    private String location;
    private VolunteerStatus status;

    // Getters and Setters

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public VolunteerAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(VolunteerAvailability availability) {
        this.availability = availability;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getPreferredActivities() {
        return preferredActivities;
    }

    public void setPreferredActivities(String preferredActivities) {
        this.preferredActivities = preferredActivities;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public VolunteerStatus getStatus() {
        return status;
    }

    public void setStatus(VolunteerStatus status) {
        this.status = status;
    }
}
