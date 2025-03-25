package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.Entity.User;
import com.example.HopeConnect.Models.Entity.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByUserType(UserType userType); // Find users by role

    boolean existsByEmail(String email); // Check if an email is already used
}
