package com.example.HopeConnect.Services;

import com.example.HopeConnect.DTO.VolunteerActivitiesDTO;
import com.example.HopeConnect.Enumes.VolunteerActivityStatus;
import com.example.HopeConnect.Models.OrphanProject;
import com.example.HopeConnect.Models.VolunteerActivities;
import com.example.HopeConnect.Models.Volunteer;
import com.example.HopeConnect.Models.Orphanage;
import com.example.HopeConnect.Repositories.OrphanProjectRepository;
import com.example.HopeConnect.Repositories.VolunteerActivitiesRepository;
import com.example.HopeConnect.Repositories.VolunteerRepository;
import com.example.HopeConnect.Repositories.OrphanageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    private OrphanProjectRepository orphanProjectRepository;

    public List<VolunteerActivitiesDTO> getAllActivities() {
        return volunteerActivitiesRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<VolunteerActivitiesDTO> getActivityById(Long id) {
        return volunteerActivitiesRepository.findById(id).map(this::convertToDTO);
    }

    public List<VolunteerActivitiesDTO> getActivitiesByVolunteerId(Long volunteerId) {
        return volunteerActivitiesRepository.findByVolunteerId(volunteerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<VolunteerActivitiesDTO> getActivitiesByOrphanageId(Long orphanageId) {
        return volunteerActivitiesRepository.findByOrphanageId(orphanageId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<VolunteerActivitiesDTO> getActivitiesByProjectId(Long projectId) {
        return volunteerActivitiesRepository.findByProjectId(projectId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    public VolunteerActivitiesDTO createActivity(VolunteerActivitiesDTO dto) {
        Volunteer volunteer = volunteerRepository.findById(dto.getVolunteerId())
                .orElseThrow(() -> new RuntimeException("Error: Volunteer not found"));

        Orphanage orphanage = (dto.getOrphanageId() != null)
                ? orphanageRepository.findById(dto.getOrphanageId()).orElse(null)
                : null;

        OrphanProject project = (dto.getProjectId() != null)
                ? orphanProjectRepository.findById(dto.getProjectId().intValue()).orElse(null)
                : null;

        // ✅ التحقق من التعارض الزمني
        List<VolunteerActivities> existingActivities = volunteerActivitiesRepository.findByVolunteerId(dto.getVolunteerId());
        for (VolunteerActivities existing : existingActivities) {
            boolean overlap = dto.getStartDate().isBefore(existing.getEndDate() != null ? existing.getEndDate() : existing.getStartDate())
                    && dto.getEndDate().isAfter(existing.getStartDate());
            if (overlap) {
                throw new RuntimeException("Error: Volunteer already has an overlapping activity.");
            }
        }

        VolunteerActivities activity = convertToEntity(dto);
        activity.setVolunteer(volunteer);
        activity.setOrphanage(orphanage);
        activity.setProject(project);

        return convertToDTO(volunteerActivitiesRepository.save(activity));
    }

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

        if (dto.getProjectId() != null) {
            OrphanProject project = orphanProjectRepository.findById(dto.getProjectId().intValue())
                    .orElseThrow(() -> new RuntimeException("Error: Project not found"));
            activity.setProject(project);
        }

        return convertToDTO(volunteerActivitiesRepository.save(activity));
    }

    public String deleteActivity(Long id) {
        if (volunteerActivitiesRepository.existsById(id)) {
            volunteerActivitiesRepository.deleteById(id);
            return "Activity deleted successfully.";
        } else {
            return "Error: Activity not found.";
        }
    }

    private VolunteerActivitiesDTO convertToDTO(VolunteerActivities activity) {
        return new VolunteerActivitiesDTO(
                activity.getId(),
                activity.getVolunteer().getId(),
                activity.getOrphanage() != null ? activity.getOrphanage().getId() : null,
                activity.getServiceType(),
                activity.getDescription(),
                activity.getAvailability(),
                activity.getStatus(),
                activity.getStartDate(),
                activity.getEndDate(),
                activity.getNotes(),
                activity.getProject() != null ? activity.getProject().getId().longValue() : null
        );
    }

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