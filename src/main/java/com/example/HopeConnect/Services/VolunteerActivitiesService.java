package com.example.HopeConnect.Services;

import com.example.HopeConnect.DTO.VolunteerActivitiesDTO;
import com.example.HopeConnect.Enumes.VolunteerActivityStatus;
import com.example.HopeConnect.Models.VolunteerActivities;
import com.example.HopeConnect.Models.Volunteer;
import com.example.HopeConnect.Models.Orphanage;
import com.example.HopeConnect.Repositories.VolunteerActivitiesRepository;
import com.example.HopeConnect.Repositories.VolunteerRepository;
import com.example.HopeConnect.Repositories.OrphanageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolunteerActivitiesService {

    @Autowired
    private VolunteerActivitiesRepository volunteerActivitiesRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private OrphanageRepository orphanageRepository;

    /**
     * Get all volunteer activities.
     */
    public List<VolunteerActivitiesDTO> getAllActivities() {
        return volunteerActivitiesRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get a volunteer activity by ID.
     */
    public Optional<VolunteerActivitiesDTO> getActivityById(Long id) {
        return volunteerActivitiesRepository.findById(id).map(this::convertToDTO);
    }

    /**
     * Get all activities for a specific volunteer.
     */
    public List<VolunteerActivitiesDTO> getActivitiesByVolunteerId(Long volunteerId) {
        return volunteerActivitiesRepository.findByVolunteerId(volunteerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get all activities for a specific orphanage.
     */
    public List<VolunteerActivitiesDTO> getActivitiesByOrphanageId(Long orphanageId) {
        return volunteerActivitiesRepository.findByOrphanageId(orphanageId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Create a new volunteer activity.
     */
    public VolunteerActivitiesDTO createActivity(VolunteerActivitiesDTO dto) {
        Volunteer volunteer = volunteerRepository.findById(dto.getVolunteerId())
                .orElseThrow(() -> new RuntimeException("Error: Volunteer not found"));

        Orphanage orphanage = (dto.getOrphanageId() != null) ?
                orphanageRepository.findById(dto.getOrphanageId()).orElse(null) : null;

        VolunteerActivities activity = convertToEntity(dto);
        activity.setVolunteer(volunteer);
        activity.setOrphanage(orphanage);

        return convertToDTO(volunteerActivitiesRepository.save(activity));
    }

    /**
     * Update an existing volunteer activity.
     */
    public VolunteerActivitiesDTO updateActivity(Long id, VolunteerActivitiesDTO dto) {
        VolunteerActivities activity = volunteerActivitiesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Activity not found"));

        activity.setServiceType(dto.getServiceType());
        activity.setDescription(dto.getDescription());
        activity.setAvailability(dto.getAvailability());
        activity.setStatus(dto.getStatus());
        activity.setStartDate(dto.getStartDate());
        activity.setEndDate(dto.getEndDate());
        activity.setNotes(dto.getNotes());

        return convertToDTO(volunteerActivitiesRepository.save(activity));
    }

    /**
     * Delete a volunteer activity by ID.
     */
    public String deleteActivity(Long id) {
        if (volunteerActivitiesRepository.existsById(id)) {
            volunteerActivitiesRepository.deleteById(id);
            return "Activity deleted successfully.";
        } else {
            return "Error: Activity not found.";
        }
    }

    /**
     * Convert `VolunteerActivities` entity to `VolunteerActivitiesDTO`.
     */
    private VolunteerActivitiesDTO convertToDTO(VolunteerActivities activity) {
        return new VolunteerActivitiesDTO(
                activity.getId(),
                activity.getVolunteer().getId(),
                (activity.getOrphanage() != null) ? activity.getOrphanage().getId() : null,
                activity.getServiceType(),
                activity.getDescription(),
                activity.getAvailability(),
                activity.getStatus(),
                activity.getStartDate(),
                activity.getEndDate(),
                activity.getNotes()
        );
    }

    /**
     * Convert `VolunteerActivitiesDTO` to `VolunteerActivities` entity.
     */
    private VolunteerActivities convertToEntity(VolunteerActivitiesDTO dto) {
        VolunteerActivities activity = new VolunteerActivities();
        activity.setServiceType(dto.getServiceType());
        activity.setDescription(dto.getDescription());
        activity.setAvailability(dto.getAvailability());
        activity.setStatus(dto.getStatus());
        activity.setStartDate(dto.getStartDate());
        activity.setEndDate(dto.getEndDate());
        activity.setNotes(dto.getNotes());
        return activity;
    }
}
