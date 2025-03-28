package com.example.HopeConnect.Services;

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

    // ✅ تحسين البحث عن جميع المتطوعين
    @Transactional(readOnly = true)
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    // ✅ البحث عن متطوع باستخدام ID مع @Transactional لتحسين الأداء
    @Transactional(readOnly = true)
    public Optional<Volunteer> getVolunteerById(Long id) {
        return volunteerRepository.findById(id);
    }

    // ✅ تحديث بيانات المتطوع مع التأكد من صحة المدخلات
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

    // ✅ حذف المتطوع مع التحقق من وجوده مسبقًا
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

    // ✅ البحث عن المتطوعين حسب الحالة (ACTIVE, PENDING, INACTIVE)
    @Transactional(readOnly = true)
    public List<Volunteer> getVolunteersByStatus(VolunteerStatus status) {
        return volunteerRepository.findByStatus(status);
    }

    // ✅ البحث عن المتطوعين حسب التوافر (FULL_TIME, PART_TIME, FLEXIBLE)
    @Transactional(readOnly = true)
    public List<Volunteer> getVolunteersByAvailability(VolunteerAvailability availability) {
        return volunteerRepository.findByAvailability(availability);
    }

    // ✅ إنشاء متطوع جديد والتحقق من البيانات
    public String createVolunteer(Volunteer volunteer) {
        try {
            if (volunteer.getUser() == null || volunteer.getUser().getId() == null) {
                return "Error: Volunteer must be linked to an existing User.";
            }

            Optional<User> existingUserOpt = userRepository.findById(volunteer.getUser().getId());

            if (existingUserOpt.isEmpty()) {
                return "Error: User does not exist.";
            }

            User user = existingUserOpt.get();

            // ✅ التحقق مما إذا كان المستخدم مسجلاً كمتطوع بالفعل
            if (volunteerRepository.findByUser(user).isPresent()) {
                return "Error: User is already registered as a volunteer.";
            }

            // ✅ تحديث نوع المستخدم إلى VOLUNTEER
            user.setUserType(UserType.VOLUNTEER);
            userRepository.save(user);

            // ✅ تعيين الوقت عند إنشاء المتطوع لأول مرة
            volunteer.setUser(user);
            volunteer.setRegisteredAt(LocalDateTime.now());
            Volunteer savedVolunteer = volunteerRepository.save(volunteer);

            return "Volunteer created successfully with ID: " + savedVolunteer.getId();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
