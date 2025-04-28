package com.example.HopeConnect.Services;

import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Service
public class UserServices {

    private static final Logger logger = LoggerFactory.getLogger(UserServices.class);

    private final Map<String, String> resetTokens = new HashMap<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;


    public Optional<User> getUserByEmailAndPassword(String email, String password) {
        try {
            return userRepository.findByEmailAndPassword(email, password);  // هذا يفترض أنك لديك دالة في الـ Repository للبحث عن الـ User
        } catch (Exception e) {
            logger.error("Error while fetching user by email and password: ", e);
            throw new RuntimeException("Error while fetching user by email and password");
        }
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    // الحصول على جميع المستخدمين
    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            logger.error("Error while fetching all users: ", e);
            throw new RuntimeException("Error while fetching users");
        }
    }

    // الحصول على مستخدم بواسطة الـ ID
    public Optional<User> getUserById(Long id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error while fetching user by id: ", e);
            throw new RuntimeException("Error while fetching user by ID");
        }
    }

    // إنشاء مستخدم جديد
    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error while creating user: ", e);
            throw new RuntimeException("Error while creating user: " + e.getMessage());
        }
    }

    public Optional<User> login(String email, String password) {
        return userRepository.findByEmail(email);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }


    @Autowired
    private VolunteerRepository volunteerRepository;
    @Autowired
    private OrphanageRepository orphanageRepository ;


//    @Autowired
//    private SponsorRepository sponsorRepository;
//
//    @Autowired
//    private DonorRepository donorRepository;


    public ResponseEntity<Map<String, Object>> deleteUser(Long id) {
        try {
            Optional<User> userOpt = userRepository.findById(id);
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User not found", "userId", id));
            }

            User user = userOpt.get();


            volunteerRepository.findByUser(user).ifPresent(volunteerRepository::delete);

//            // 🧹 حذف الداعم إن وجد
//            sponsorRepository.findByUser(user).ifPresent(sponsorRepository::delete);
//
//            // 🧹 حذف المتبرع إن وجد
//            donorRepository.findByUser(user).ifPresent(donorRepository::delete);

            orphanageRepository.findByManager(user).ifPresent(orphanageRepository::delete);



            // 🧹 أخيرًا حذف المستخدم نفسه
            userRepository.deleteById(id);

            return ResponseEntity.ok(
                    Map.of("message", "User deleted successfully", "userId", id)
            );

        } catch (Exception e) {
            logger.error("Error while deleting user with ID: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error while deleting user", "details", e.getMessage()));
        }
    }

    // تحديث مستخدم
    public ResponseEntity<?> updateUser(Long id, User updatedUser) {
        try {
            Optional<User> existingUser = userRepository.findById(id);
            if (existingUser.isPresent()) {
                User user = existingUser.get();
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                user.setPhone(updatedUser.getPhone());
                // أضف هنا باقي الحقول التي تريد تحديثها

                User savedUser = userRepository.save(user);
                return ResponseEntity.ok(savedUser);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with ID: " + id);
            }
        } catch (Exception e) {
            logger.error("Error while updating user with ID: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while updating user: " + e.getMessage());
        }
    }
}