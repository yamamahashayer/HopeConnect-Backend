package com.example.HopeConnect.DTO;

import com.example.HopeConnect.Enumes.VolunteerAvailability;
import com.example.HopeConnect.Enumes.VolunteerStatus;
import com.example.HopeConnect.Models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
