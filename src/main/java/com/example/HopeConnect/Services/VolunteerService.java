package com.example.HopeConnect.Services;

import com.example.HopeConnect.Models.Entity.User;
import com.example.HopeConnect.Models.Entity.UserType;
import com.example.HopeConnect.Models.Entity.Volunteer;
import com.example.HopeConnect.Repositories.VolunteerRepository;
import com.example.HopeConnect.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    // إرجاع جميع المتطوعين
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    // البحث عن متطوع باستخدام ID
    public Optional<Volunteer> getVolunteerById(Long id) {
        return volunteerRepository.findById(id);
    }


    // تحديث بيانات متطوع
    public String updateVolunteer(Long id, Volunteer updatedVolunteer) {
        return volunteerRepository.findById(id).map(existing -> {
            try {
                existing.setSkills(updatedVolunteer.getSkills());
                existing.setAvailability(updatedVolunteer.getAvailability());
                existing.setExperienceYears(updatedVolunteer.getExperienceYears());
                existing.setPreferredActivities(updatedVolunteer.getPreferredActivities());
                existing.setLocation(updatedVolunteer.getLocation());
                existing.setStatus(updatedVolunteer.getStatus());

                // تحديث بيانات المستخدم أيضًا
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

    // حذف متطوع
    public String deleteVolunteer(Long id) {
        try {
            Optional<Volunteer> volunteer = volunteerRepository.findById(id);
            if (volunteer.isPresent()) {
                User user = volunteer.get().getUser();
                volunteerRepository.deleteById(id);
                userRepository.deleteById(user.getId()); // حذف المستخدم المرتبط
                return "Volunteer deleted successfully.";
            }
            return "Error: Volunteer not found.";
        } catch (Exception e) {
            logger.error("Error deleting volunteer: {}", e.getMessage(), e);
            return "Error: " + e.getMessage();
        }
    }

    // البحث عن متطوعين حسب الحالة (ACTIVE, PENDING, etc.)
    public List<Volunteer> getVolunteersByStatus(Volunteer.Status status) {
        return volunteerRepository.findByStatus(status);
    }



    // البحث عن متطوعين حسب التوافر (FULL_TIME, PART_TIME, etc.)
    public List<Volunteer> getVolunteersByAvailability(Volunteer.Availability availability) {
        return volunteerRepository.findByAvailability(availability);
    }



    // البحث عن متطوعين حسب الحالة والتوافر
    public List<Volunteer> getVolunteersByStatusAndAvailability(Volunteer.Status status, Volunteer.Availability availability) {
        return volunteerRepository.findByStatusAndAvailability(status, availability);
    }






    public String createVolunteer(Volunteer volunteer) {
        try {
            if (volunteer.getUser() == null) {
                logger.error("❌ Error: Volunteer must be linked to an existing User.");
                return "Error: Volunteer must be linked to an existing User.";
            }

            // استخراج بيانات المستخدم
            User user = volunteer.getUser();
            logger.info("📌 Received User: " + user);

            // التحقق مما إذا كان المستخدم موجودًا بالفعل عبر البريد الإلكتروني
            Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

            if (existingUser.isEmpty()) {
                user.setUserType(UserType.VOLUNTEER);
                user = userRepository.save(user);
                logger.info("✅ New User Created: " + user);
            } else {
                user = existingUser.get();
                logger.info("🔄 Existing User Found: " + user);
            }

            // التأكد من أن `user_id` ليس فارغًا
            if (user.getId() == null) {
                logger.error("❌ Error: User ID is null after saving.");
                return "Error: User ID is null after saving.";
            }

            // تعيين `User` في `Volunteer`
            volunteer.setUser(user);
            logger.info("📌 Final Volunteer Data before Save: " + volunteer);

            // حفظ `Volunteer`
            Volunteer savedVolunteer = volunteerRepository.save(volunteer);
            logger.info("✅ Saved Volunteer: " + savedVolunteer);

            if (savedVolunteer.getId() == null) {
                logger.error("❌ Error: Volunteer was not saved successfully.");
                return "Error: Volunteer was not saved successfully.";
            }

            return "Volunteer created successfully with ID: " + savedVolunteer.getId();
        } catch (Exception e) {
            logger.error("❌ Error creating volunteer: {}", e.getMessage(), e);
            return "Error: " + e.getMessage();
        }
    }
























}
