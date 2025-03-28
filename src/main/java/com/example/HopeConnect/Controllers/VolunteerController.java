package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Models.Entity.Volunteer;
import com.example.HopeConnect.Services.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
