package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.DTO.VolunteerDTO;
import com.example.HopeConnect.DTO.VolunteerRegistrationDTO;
import com.example.HopeConnect.Enumes.*;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Models.Volunteer;
import com.example.HopeConnect.Repositories.UserRepository;
import com.example.HopeConnect.Repositories.VolunteerActivitiesRepository;
import com.example.HopeConnect.Repositories.VolunteerRepository;
import com.example.HopeConnect.Services.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.HopeConnect.Enumes.VolunteerStatus;
import com.example.HopeConnect.Enumes.VolunteerAvailability;
import com.example.HopeConnect.DTO.VolunteerRegistrationDTO;

import java.time.LocalDateTime;
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
    @Autowired
    private VolunteerActivitiesRepository volunteerActivitiesRepository;


    @GetMapping("/all")
    public ResponseEntity<List<VolunteerDTO>> getAllVolunteers() {
        List<VolunteerDTO> list = volunteerService.getAllVolunteers();
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

    @PostMapping("/register")
    public ResponseEntity<?> registerVolunteer(@RequestBody VolunteerRegistrationDTO dto) {
        String response = volunteerService.registerVolunteerWithUser(dto);
        return response.contains("Error")
                ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
                : ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/status")
    public ResponseEntity<?> getVolunteersByStatus(@RequestParam String status) {
        try {
            VolunteerStatus volunteerStatus = VolunteerStatus.valueOf(status.toUpperCase());

            List<Volunteer> volunteers = volunteerService.getVolunteersByStatus(volunteerStatus);
            return volunteers.isEmpty()
                    ? ResponseEntity.status(HttpStatus.NO_CONTENT).body("No volunteers found with status: " + status)
                    : ResponseEntity.ok(volunteers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid status value: " + status);
        }
    }

    @GetMapping("/availability")
    public ResponseEntity<?> getVolunteersByAvailability(@RequestParam String availability) {
        try {
            VolunteerAvailability volunteerAvailability = VolunteerAvailability.valueOf(availability.toUpperCase());

            List<Volunteer> volunteers = volunteerService.getVolunteersByAvailability(volunteerAvailability);
            return volunteers.isEmpty()
                    ? ResponseEntity.status(HttpStatus.NO_CONTENT).body("No volunteers found with availability: " + availability)
                    : ResponseEntity.ok(volunteers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid availability value: " + availability);
        }
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<?> deleteAllVolunteers() {
        try {
            // أولاً حذف كل النشاطات المرتبطة
            volunteerActivitiesRepository.deleteAll();

            // بعد ذلك حذف المتطوعين
            volunteerRepository.deleteAll();

            return ResponseEntity.ok("All volunteers and their activities have been deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while deleting volunteers: " + e.getMessage());
        }
    }


    @PutMapping("/addUser/{userId}")
    public ResponseEntity<?> addExistingUserAsVolunteer(@PathVariable Long userId) {
        try {
            Optional<User> existingUserOpt = userRepository.findById(userId);

            if (existingUserOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: User ID " + userId + " not found.");
            }

            User user = existingUserOpt.get();

            Optional<Volunteer> existingVolunteer = volunteerRepository.findByUser(user);
            if (existingVolunteer.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: User is already a volunteer.");
            }

            // ✅ تحويل نوع المستخدم إلى VOLUNTEER
            user.setUserType(UserType.VOLUNTEER);
            userRepository.save(user);

            // إنشاء متطوع جديد
            Volunteer volunteer = new Volunteer();
            volunteer.setUser(user);
            volunteer.setSkills("Not specified");
            volunteer.setAvailability(VolunteerAvailability.FLEXIBLE);
            volunteer.setExperienceYears(0);
            volunteer.setPreferredActivities("Not specified");
            volunteer.setLocation("Not specified");
            volunteer.setStatus(VolunteerStatus.PENDING);
            volunteer.setRegisteredAt(LocalDateTime.now());


            volunteerRepository.save(volunteer);

            return ResponseEntity.ok("User ID " + userId + " has been successfully added as a volunteer.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


}
