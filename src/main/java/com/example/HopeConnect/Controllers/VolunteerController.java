package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Models.Volunteer;
import com.example.HopeConnect.Repositories.UserRepository;
import com.example.HopeConnect.Repositories.VolunteerRepository;
import com.example.HopeConnect.Services.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;
    @Autowired
    private VolunteerRepository volunteerRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Volunteer>> getAllVolunteers() {
        List<Volunteer> list = volunteerService.getAllVolunteers();
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getVolunteerById(@PathVariable Long id) {
        Optional<Volunteer> volunteer = volunteerService.getVolunteerById(id);

        if (volunteer.isPresent()) {
            return ResponseEntity.ok(volunteer.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Volunteer with ID " + id + " not found.");
        }
    }

    @PutMapping("up/{id}")
    public ResponseEntity<?> updateVolunteer(@PathVariable Long id, @RequestBody Volunteer volunteer) {
        String response = volunteerService.updateVolunteer(id, volunteer);
        return response.contains("Error") ? ResponseEntity.status(404).body(response) : ResponseEntity.ok(response);
    }

    @DeleteMapping("del/{id}")
    public ResponseEntity<?> deleteVolunteer(@PathVariable Long id) {
        String response = volunteerService.deleteVolunteer(id);
        return response.contains("Error") ? ResponseEntity.status(404).body(response) : ResponseEntity.ok(response);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createVolunteer(@RequestBody Volunteer volunteer) {
        String response = volunteerService.createVolunteer(volunteer);
        return response.contains("Error") ? ResponseEntity.status(400).body(response) : ResponseEntity.status(201).body(response);
    }
    @PutMapping("/addUser/{userId}")
    public ResponseEntity<?> addExistingUserAsVolunteer(@PathVariable Long userId) {
        try {
            // البحث عن المستخدم في قاعدة البيانات
            Optional<User> existingUserOpt = userRepository.findById(userId);

            if (existingUserOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User ID " + userId + " not found.");
            }

            User user = existingUserOpt.get();

            // التحقق مما إذا كان المستخدم مسجلاً بالفعل كمتطوع
            Optional<Volunteer> existingVolunteer = volunteerRepository.findByUser(user);
            if (existingVolunteer.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: User is already a volunteer.");
            }

            // تحويل المستخدم إلى متطوع
            Volunteer volunteer = new Volunteer();
            volunteer.setUser(user);
            volunteer.setSkills("Not specified");
            volunteer.setAvailability(Volunteer.Availability.FLEXIBLE);
            volunteer.setExperienceYears(0);
            volunteer.setPreferredActivities("Not specified");
            volunteer.setLocation("Not specified");
            volunteer.setStatus(Volunteer.Status.PENDING);

            // حفظ المتطوع الجديد
            volunteerRepository.save(volunteer);

            return ResponseEntity.ok("User ID " + userId + " has been successfully added as a volunteer.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity<List<Volunteer>> getVolunteersByStatus(@RequestParam Volunteer.Status status) {
        List<Volunteer> volunteers = volunteerService.getVolunteersByStatus(status);
        return volunteers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(volunteers);
    }
    @GetMapping("/availability")
    public ResponseEntity<List<Volunteer>> getVolunteersByAvailability(@RequestParam Volunteer.Availability availability) {
        List<Volunteer> volunteers = volunteerService.getVolunteersByAvailability(availability);
        return volunteers.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(volunteers);
    }

}
