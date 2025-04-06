package com.example.HopeConnect.DTO;

import com.example.HopeConnect.Enumes.VolunteerActivityStatus;
import com.example.HopeConnect.Enumes.VolunteerActivityType;
import com.example.HopeConnect.Enumes.VolunteerAvailability;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class VolunteerActivitiesDTO {

    private Long id;

    @NotNull(message = "Volunteer ID is required.")
    private Long volunteerId;

    private Long orphanageId;

    @NotNull(message = "Service type is required.")
    @Enumerated(EnumType.STRING)
    private VolunteerActivityType serviceType;

    @NotBlank(message = "Description is mandatory.")
    @Size(max = 500, message = "Description cannot exceed 500 characters.")
    private String description;

    @NotNull(message = "Availability is required.")
    @Enumerated(EnumType.STRING)
    private VolunteerAvailability availability;

    @NotNull(message = "Activity status is required.")
    @Enumerated(EnumType.STRING)
    private VolunteerActivityStatus status;

    @Future(message = "Start date must be in the future.")
    private LocalDateTime startDate;

    private LocalDateTime endDate; // Optional

    @Size(max = 500, message = "Notes must be under 500 characters.")
    private String notes;

    // ✅ مضاف حديثًا لربط النشاط بالمشروع
    private Long projectId;

    // ✅ Constructor كامل (إذا استخدمت new ...)
    public VolunteerActivitiesDTO(
            Long id,
            Long volunteerId,
            Long orphanageId,
            VolunteerActivityType serviceType,
            String description,
            VolunteerAvailability availability,
            VolunteerActivityStatus status,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String notes,
            Long projectId
    ) {
        this.id = id;
        this.volunteerId = volunteerId;
        this.orphanageId = orphanageId;
        this.serviceType = serviceType;
        this.description = description;
        this.availability = availability;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
        this.projectId = projectId;
    }
}
