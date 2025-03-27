package com.example.HopeConnect.Services;

import com.example.HopeConnect.Models.Entity.Volunteer;
import com.example.HopeConnect.Repositories.VolunteerRepository;
import com.example.HopeConnect.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    public Optional<Volunteer> getVolunteerById(Long id) {
        return volunteerRepository.findById(id);
    }



    public List<Volunteer> getVolunteersByStatus(Volunteer.Status status) {
        return volunteerRepository.findByStatus(status);
    }

    public List<Volunteer> getVolunteersByCity(String city) {
        return volunteerRepository.findByCity(city);
    }

    public List<Volunteer> getVolunteersByAvailability(Volunteer.Availability availability) {
        return volunteerRepository.findByAvailability(availability);
    }

    public List<Volunteer> getVolunteersByStatusAndCity(Volunteer.Status status, String city) {
        return volunteerRepository.findByStatusAndCity(status, city);
    }

    public List<Volunteer> getVolunteersByStatusAndAvailability(Volunteer.Status status, Volunteer.Availability availability) {
        return volunteerRepository.findByStatusAndAvailability(status, availability);
    }

    public String createVolunteer(Volunteer volunteer) {
        if (volunteer.getUser() == null || volunteer.getUser().getId() == null) {
            return "Error: Volunteer must be linked to an existing User.";
        }
        volunteerRepository.save(volunteer);
        return "Volunteer created successfully.";
    }

    public String updateVolunteer(Long id, Volunteer updatedVolunteer) {
        return volunteerRepository.findById(id).map(existing -> {
            existing.setSkills(updatedVolunteer.getSkills());
            existing.setAvailability(updatedVolunteer.getAvailability());
            existing.setExperienceYears(updatedVolunteer.getExperienceYears());
            existing.setPreferredActivities(updatedVolunteer.getPreferredActivities());
            existing.setLocation(updatedVolunteer.getLocation());
            existing.setStatus(updatedVolunteer.getStatus());

            // تحديث بيانات المستخدم أيضًا
            existing.getUser().setName(updatedVolunteer.getUser().getName());
            existing.getUser().setEmail(updatedVolunteer.getUser().getEmail());
            existing.getUser().setPhone(updatedVolunteer.getUser().getPhone());

            volunteerRepository.save(existing);
            return "Volunteer updated successfully.";
        }).orElse("Error: Volunteer not found.");
    }

    public String deleteVolunteer(Long id) {
        Optional<Volunteer> volunteer = volunteerRepository.findById(id);
        if (volunteer.isPresent()) {
            userRepository.deleteById(volunteer.get().getUser().getId());  // حذف المستخدم أيضًا
            volunteerRepository.deleteById(id);
            return "Volunteer deleted successfully.";
        }
        return "Error: Volunteer not found.";
    }
}