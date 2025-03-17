package com.example.HopeConnect.Repositories;



import com.example.HopeConnect.Models.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository  // Marks this interface as a Repository (Data Access Layer)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
