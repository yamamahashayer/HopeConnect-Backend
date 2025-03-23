package com.example.HopeConnect.Services;

import com.example.HopeConnect.Models.Entity.User;
import com.example.HopeConnect.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServices {

    private static final Logger logger = LoggerFactory.getLogger(UserServices.class);

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            logger.error("Error while fetching all users: ", e);
            throw new RuntimeException("Error while fetching users");
        }
    }

    public Optional<User> getUserById(Long id) {
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error while fetching user by id: ", e);
            throw new RuntimeException("Error while fetching user by ID");
        }
    }

    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error while creating user: ", e);
            throw new RuntimeException("Error while creating user: " + e.getMessage());
        }
    }

    public ResponseEntity<Map<String, Object>> deleteUser(Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                userRepository.deleteById(id);
                Map<String, Object> response = new HashMap<>();
                response.put("message", "User deleted successfully");
                response.put("userId", id);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "User not found", "userId", id));
            }
        } catch (Exception e) {
            logger.error("Error while deleting user with ID: " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error while deleting user", "details", e.getMessage()));
        }
    }


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