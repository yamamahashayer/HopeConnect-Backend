package com.example.HopeConnect.Repositories;



import com.example.HopeConnect.Models.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.HopeConnect.Models.Entity.UserType;
import java.util.Optional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email); // Find a user by email

    List<User> findByUserType(UserType userType); // Find users by role

    boolean existsByEmail(String email); // Check if an email is already used
}
