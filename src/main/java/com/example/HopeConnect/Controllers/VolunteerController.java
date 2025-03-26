package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Models.Entity.Volunteer;
import com.example.HopeConnect.Services.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
///////////////
@RestController
@RequestMapping("/volunteers")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @GetMapping
    public ResponseEntity<List<Volunteer>> getAllVolunteers() {
        List<Volunteer> list = volunteerService.getAllVolunteers();
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVolunteerById(@PathVariable Long id) {
        Optional<Volunteer> volunteer = volunteerService.getVolunteerById(id);
        return volunteer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Volunteer> getVolunteerByEmail(@PathVariable String email) {
        Optional<Volunteer> volunteer = volunteerService.getVolunteerByEmail(email);
        return volunteer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }




    @GetMapping("/filter")
    public ResponseEntity<List<Volunteer>> filterVolunteers(
            @RequestParam(required = false) Volunteer.Status status,
            @RequestParam(required = false) Volunteer.Availability availability,
            @RequestParam(required = false) String city
    ) {
        List<Volunteer> result;

        if (status != null && city != null) {
            result = volunteerService.getVolunteersByStatusAndCity(status, city);
        } else if (status != null && availability != null) {
            result = volunteerService.getVolunteersByStatusAndAvailability(status, availability);
        } else if (availability != null) {
            result = volunteerService.getVolunteersByAvailability(availability);
        } else if (city != null) {
            result = volunteerService.getVolunteersByCity(city);
        } else if (status != null) {
            result = volunteerService.getVolunteersByStatus(status);
        } else {
            result = volunteerService.getAllVolunteers();
        }

        return result.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Volunteer> createVolunteer(@RequestBody Volunteer volunteer) {
        return ResponseEntity.status(201).body(volunteerService.createVolunteer(volunteer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVolunteer(@PathVariable Long id, @RequestBody Volunteer volunteer) {
        try {
            Volunteer updated = volunteerService.updateVolunteer(id, volunteer);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVolunteer(@PathVariable Long id) {
        volunteerService.deleteVolunteer(id);
        return ResponseEntity.ok("Volunteer deleted successfully");
    }
}
