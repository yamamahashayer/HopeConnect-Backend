package com.example.HopeConnect.DTO;

import com.example.HopeConnect.Enumes.VolunteerAvailability;
import com.example.HopeConnect.Enumes.VolunteerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerDTO {
    private Long userId;

    private Long Volunteer_id;
    private String name;
    private String email;
    private String phone;
    private String city;
    private VolunteerAvailability availability;
    private int experienceYears;
    private String preferredActivities;
    private String skills;
    private String location;
    private VolunteerStatus status;
    private LocalDateTime registeredAt;
}
