package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Models.Task;
import com.example.HopeConnect.Models.Donation;
import com.example.HopeConnect.Models.Volunteer;
import com.example.HopeConnect.Repositories.TaskRepository;
import com.example.HopeConnect.Repositories.DonationRepository;
import com.example.HopeConnect.Repositories.VolunteerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @GetMapping
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @PostMapping
    public Task create(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Task updatedTask) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Task existingTask = optionalTask.get();

        // تعيين الحقول البسيطة
        existingTask.setPickupLat(updatedTask.getPickupLat());
        existingTask.setPickupLng(updatedTask.getPickupLng());
        existingTask.setDropoffLat(updatedTask.getDropoffLat());
        existingTask.setDropoffLng(updatedTask.getDropoffLng());
        existingTask.setStatus(updatedTask.getStatus());
        existingTask.setScheduledTime(updatedTask.getScheduledTime());

        // تعيين Donation بناءً على ID
        if (updatedTask.getDonation() != null && updatedTask.getDonation().getId() != null) {
            Optional<Donation> donationOpt = donationRepository.findById(updatedTask.getDonation().getId());
            if (donationOpt.isPresent()) {
                existingTask.setDonation(donationOpt.get());
            } else {
                return ResponseEntity.badRequest().body("Donation with ID " + updatedTask.getDonation().getId() + " not found");
            }
        }

        // تعيين Volunteer بناءً على ID
        if (updatedTask.getVolunteer() != null && updatedTask.getVolunteer().getId() != null) {
            Optional<Volunteer> volunteerOpt = volunteerRepository.findById(updatedTask.getVolunteer().getId());
            if (volunteerOpt.isPresent()) {
                existingTask.setVolunteer(volunteerOpt.get());
            } else {
                return ResponseEntity.badRequest().body("Volunteer with ID " + updatedTask.getVolunteer().getId() + " not found");
            }
        }

        Task savedTask = taskRepository.save(existingTask);
        return ResponseEntity.ok(savedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        taskRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
