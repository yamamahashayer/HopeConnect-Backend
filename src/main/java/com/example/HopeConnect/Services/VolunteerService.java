package com.example.HopeConnect.Services;

import com.example.HopeConnect.Enumes.VolunteerStatus;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Enumes.UserType;
import com.example.HopeConnect.Models.Volunteer;
import com.example.HopeConnect.Repositories.VolunteerRepository;
import com.example.HopeConnect.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.HopeConnect.Enumes.VolunteerAvailability;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.List;

@Service
public class VolunteerService {

    private static final Logger logger = LoggerFactory.getLogger(VolunteerService.class);

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
    public String updateVolunteer(Long id, Volunteer updatedVolunteer) {
        return volunteerRepository.findById(id).map(existing -> {
            try {
                existing.setSkills(updatedVolunteer.getSkills());
                existing.setAvailability(updatedVolunteer.getAvailability());
                existing.setExperienceYears(updatedVolunteer.getExperienceYears());
                existing.setPreferredActivities(updatedVolunteer.getPreferredActivities());
                existing.setLocation(updatedVolunteer.getLocation());
                existing.setStatus(updatedVolunteer.getStatus());

                User user = existing.getUser();
                user.setName(updatedVolunteer.getUser().getName());
                user.setEmail(updatedVolunteer.getUser().getEmail());
                user.setPhone(updatedVolunteer.getUser().getPhone());
                user.setCity(updatedVolunteer.getUser().getCity());

                userRepository.save(user);
                volunteerRepository.save(existing);

                return "Volunteer updated successfully.";
            } catch (Exception e) {
                logger.error("Error updating volunteer: {}", e.getMessage(), e);
                return "Error updating volunteer: " + e.getMessage();
            }
        }).orElse("Error: Volunteer not found.");
    }

    public String deleteVolunteer(Long id) {
        try {
            Optional<Volunteer> volunteerOpt = volunteerRepository.findById(id);

            if (volunteerOpt.isEmpty()) {
                return "Error: Volunteer with ID " + id + " not found.";
            }
            volunteerRepository.deleteById(id);

            return "Volunteer with ID " + id + " has been successfully deleted.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }


    public List<Volunteer> getVolunteersByStatus(VolunteerStatus status) {
        return volunteerRepository.findByStatus(status);
    }

    public List<Volunteer> getVolunteersByAvailability(VolunteerAvailability availability) {
        return volunteerRepository.findByAvailability(availability);
    }

    public String createVolunteer(Volunteer volunteer) {
        try {
            if (volunteer.getUser() == null) {
                return "Error: Volunteer must be linked to an existing User.";
            }

            User user = volunteer.getUser();

            Optional<User> existingUserOpt = userRepository.findByEmail(user.getEmail());

            if (existingUserOpt.isPresent()) {
                User existingUser = existingUserOpt.get();
                existingUser.setName(user.getName());
                existingUser.setPassword(user.getPassword());
                existingUser.setPhone(user.getPhone());
                existingUser.setNationality(user.getNationality());
                existingUser.setCountry(user.getCountry());
                existingUser.setCity(user.getCity());
                existingUser.setUserType(UserType.VOLUNTEER);

                user = userRepository.save(existingUser);
            } else {
                user.setUserType(UserType.VOLUNTEER);
                user = userRepository.save(user);
            }

            if (user.getId() == null) {
                return "Error: User ID is null after saving.";
            }

            Optional<Volunteer> existingVolunteer = volunteerRepository.findByUser(user);

            if (existingVolunteer.isPresent()) {
                return "Error: User is already registered as a volunteer.";
            }
            volunteer.setUser(user);
            Volunteer savedVolunteer = volunteerRepository.save(volunteer);
            if (savedVolunteer.getId() == null) {
                return "Error: Volunteer was not saved successfully.";
            }

            return "Volunteer created successfully with ID: " + savedVolunteer.getId();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }



}