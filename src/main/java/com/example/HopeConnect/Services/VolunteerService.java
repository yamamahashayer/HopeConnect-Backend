package com.example.HopeConnect.Services;
import com.example.HopeConnect.Models.Entity.Volunteer;
import com.example.HopeConnect.Repositories.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;
@Service


public class VolunteerService {
    @Autowired
    private VolunteerRepository volunteerRepository;

    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    public Optional<Volunteer> getVolunteerById(Long id) {
        return volunteerRepository.findById(id);
    }

    public Optional<Volunteer> getVolunteerByEmail(String email) {
        return volunteerRepository.findByEmail(email);
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

    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public Volunteer updateVolunteer(Long id, Volunteer updatedVolunteer) {
        return volunteerRepository.findById(id).map(existing -> {
            existing.setSkills(updatedVolunteer.getSkills());
            existing.setAvailability(updatedVolunteer.getAvailability());
            existing.setExperienceYears(updatedVolunteer.getExperienceYears());
            existing.setPreferredActivities(updatedVolunteer.getPreferredActivities());
            existing.setLocation(updatedVolunteer.getLocation());
            existing.setStatus(updatedVolunteer.getStatus());
            return volunteerRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Volunteer not found"));
    }

    public void deleteVolunteer(Long id) {
        volunteerRepository.deleteById(id);
    }

}
