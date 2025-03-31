package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.DTO.VolunteerActivitiesDTO;
import com.example.HopeConnect.Errors.ErrorResponse;
import com.example.HopeConnect.Services.VolunteerActivitiesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/volunteer-activities")
public class VolunteerActivitiesController {

    private final VolunteerActivitiesService volunteerActivitiesService;

    public VolunteerActivitiesController(VolunteerActivitiesService volunteerActivitiesService) {
        this.volunteerActivitiesService = volunteerActivitiesService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<VolunteerActivitiesDTO>> getAllActivities() {
        List<VolunteerActivitiesDTO> activities = volunteerActivitiesService.getAllActivities();
        return activities.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(activities);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getActivityById(@PathVariable Long id) {
        Optional<VolunteerActivitiesDTO> activity = volunteerActivitiesService.getActivityById(id);

        if (activity.isPresent()) {
            return ResponseEntity.ok(activity.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Error: Activity with ID " + id + " not found."));
        }
    }



    @GetMapping("/volunteer/{volunteerId}")
    public ResponseEntity<?> getActivitiesByVolunteer(@PathVariable Long volunteerId) {
        List<VolunteerActivitiesDTO> activities = volunteerActivitiesService.getActivitiesByVolunteerId(volunteerId);
        return activities.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ErrorResponse("No activities found for volunteer ID: " + volunteerId))
                : ResponseEntity.ok(activities);
    }



    @GetMapping("/orphanage/{orphanageId}")
    public ResponseEntity<?> getActivitiesByOrphanage(@PathVariable Long orphanageId) {
        List<VolunteerActivitiesDTO> activities = volunteerActivitiesService.getActivitiesByOrphanageId(orphanageId);
        return activities.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).body("No activities found for orphanage ID: " + orphanageId)
                : ResponseEntity.ok(activities);
    }


    @PostMapping("/new")
    public ResponseEntity<?> createActivity(@RequestBody VolunteerActivitiesDTO activityDTO) {
        try {
            VolunteerActivitiesDTO savedActivity = volunteerActivitiesService.createActivity(activityDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedActivity);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateActivity(@PathVariable Long id, @RequestBody VolunteerActivitiesDTO activityDTO) {
        try {
            VolunteerActivitiesDTO updatedActivity = volunteerActivitiesService.updateActivity(id, activityDTO);
            return ResponseEntity.ok(updatedActivity);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable Long id) {
        String response = volunteerActivitiesService.deleteActivity(id);
        return response.contains("Error")
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(response)
                : ResponseEntity.ok(response);
    }
}
