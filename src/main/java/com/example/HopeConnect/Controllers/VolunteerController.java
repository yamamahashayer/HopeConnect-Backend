package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Models.Entity.Volunteer;
import com.example.HopeConnect.Services.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Volunteer> getVolunteerById(@PathVariable Long id) {
        return volunteerService.getVolunteerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



//    @GetMapping("/email/{email}")
//    public ResponseEntity<?> getVolunteerByEmail(@PathVariable String email) {
//       // Optional<Volunteer> volunteer = volunteerService.getVolunteerByEmail(email);
//      //  return volunteer.map(ResponseEntity::ok)
//          //      .orElseGet(() -> ResponseEntity.status(404).body("Volunteer not found by email"));
//    }

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

        return result.isEmpty() ? ResponseEntity.status(204).body(null) : ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> createVolunteer(@RequestBody Volunteer volunteer) {
        String response = volunteerService.createVolunteer(volunteer);
        return response.contains("Error") ? ResponseEntity.status(400).body(response) : ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVolunteer(@PathVariable Long id, @RequestBody Volunteer volunteer) {
        String response = volunteerService.updateVolunteer(id, volunteer);
        return response.contains("Error") ? ResponseEntity.status(404).body(response) : ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVolunteer(@PathVariable Long id) {
        String response = volunteerService.deleteVolunteer(id);
        return response.contains("Error") ? ResponseEntity.status(404).body(response) : ResponseEntity.ok(response);
    }
}