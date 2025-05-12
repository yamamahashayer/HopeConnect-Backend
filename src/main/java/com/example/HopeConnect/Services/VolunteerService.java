package com.example.HopeConnect.Services;

import com.example.HopeConnect.DTO.VolunteerDTO;
import com.example.HopeConnect.Enumes.VolunteerStatus;
import com.example.HopeConnect.Enumes.VolunteerAvailability;

import com.example.HopeConnect.Enumes.UserType;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Models.Volunteer;
import com.example.HopeConnect.Repositories.VolunteerRepository;
import com.example.HopeConnect.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.HopeConnect.DTO.VolunteerRegistrationDTO;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class VolunteerService {

    private static final Logger logger = LoggerFactory.getLogger(VolunteerService.class);

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private UserRepository userRepository;


    public Optional<Volunteer> getVolunteerByUserId(Long userId) {
        return volunteerRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public Optional<Volunteer> getVolunteerById(Long id) {
        return volunteerRepository.findById(id);
    }

    public String updateVolunteer(Long id, Volunteer updatedVolunteer) {
        return volunteerRepository.findById(id).map(existing -> {
            try {
                if (updatedVolunteer.getSkills() != null && !updatedVolunteer.getSkills().isEmpty()) {
                    existing.setSkills(updatedVolunteer.getSkills());
                }

                if (updatedVolunteer.getAvailability() != null) {
                    existing.setAvailability(updatedVolunteer.getAvailability());
                }

                if (updatedVolunteer.getExperienceYears() >= 0) {
                    existing.setExperienceYears(updatedVolunteer.getExperienceYears());
                }

                if (updatedVolunteer.getPreferredActivities() != null && !updatedVolunteer.getPreferredActivities().isEmpty()) {
                    existing.setPreferredActivities(updatedVolunteer.getPreferredActivities());
                }

                if (updatedVolunteer.getLocation() != null && !updatedVolunteer.getLocation().isEmpty()) {
                    existing.setLocation(updatedVolunteer.getLocation());
                }

                if (updatedVolunteer.getStatus() != null) {
                    existing.setStatus(updatedVolunteer.getStatus());
                }

                // ✅ تحديث معلومات المستخدم المرتبط
                User user = existing.getUser();
                if (updatedVolunteer.getUser() != null) {
                    User updatedUser = updatedVolunteer.getUser();

                    if (updatedUser.getName() != null && !updatedUser.getName().isEmpty()) {
                        user.setName(updatedUser.getName());
                    }

                    if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
                        user.setEmail(updatedUser.getEmail());
                    }

                    if (updatedUser.getPhone() != null && !updatedUser.getPhone().isEmpty()) {
                        user.setPhone(updatedUser.getPhone());
                    }

                    if (updatedUser.getCity() != null && !updatedUser.getCity().isEmpty()) {
                        user.setCity(updatedUser.getCity());
                    }
                }

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

    @Transactional(readOnly = true)
    public List<Volunteer> getVolunteersByStatus(VolunteerStatus status) {
        return volunteerRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Volunteer> getVolunteersByAvailability(VolunteerAvailability availability) {
        return volunteerRepository.findByAvailability(availability);
    }


    public String registerVolunteerWithUser(VolunteerRegistrationDTO dto) {
        try {
            // جلب كائن المستخدم من DTO
            User user = dto.getUser();

            // تعيين نوع المستخدم كـ VOLUNTEER
            user.setUserType(UserType.VOLUNTEER);
            userRepository.save(user);

            // إنشاء المتطوع وربطه بالمستخدم
            Volunteer volunteer = new Volunteer();
            volunteer.setUser(user);
            volunteer.setSkills(dto.getSkills());
            volunteer.setAvailability(dto.getAvailability());
            volunteer.setExperienceYears(dto.getExperienceYears());
            volunteer.setPreferredActivities(dto.getPreferredActivities());
            volunteer.setLocation(dto.getLocation());
            volunteer.setStatus(dto.getStatus());
            volunteer.setRegisteredAt(LocalDateTime.now());

            volunteerRepository.save(volunteer);

            return "Volunteer with user registered successfully. Volunteer ID: " + volunteer.getId();
        } catch (Exception e) {
            return "Error registering volunteer with user: " + e.getMessage();
        }
    }







    public VolunteerDTO convertToDTO(Volunteer volunteer) {
        User user = volunteer.getUser();

        return new VolunteerDTO(

                user.getId(),
                volunteer.getId(),

                user != null ? user.getName() : "N/A",
                user != null ? user.getEmail() : "N/A",
                user != null ? user.getPhone() : "N/A",
                user != null ? user.getCity() : "N/A",
                volunteer.getAvailability() != null ? volunteer.getAvailability() : VolunteerAvailability.FLEXIBLE,
                volunteer.getExperienceYears(),
                volunteer.getPreferredActivities() != null ? volunteer.getPreferredActivities() : "Not specified",
                volunteer.getSkills() != null ? volunteer.getSkills() : "Not specified",
                volunteer.getLocation() != null ? volunteer.getLocation() : "Not specified",
                volunteer.getStatus() != null ? volunteer.getStatus() : VolunteerStatus.PENDING,
                volunteer.getRegisteredAt()
        );
    }
    public List<VolunteerDTO> getAllVolunteers() {
        return volunteerRepository.findAll().stream()
                .filter(v -> v.getUser() != null)
                .map(this::convertToDTO)
                .toList();
    }



}