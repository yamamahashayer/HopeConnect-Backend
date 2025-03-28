package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Models.Volunteer;
import java.util.Optional;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {


   List<Volunteer> findByStatus(Volunteer.Status status);
   List<Volunteer> findByAvailability(Volunteer.Availability availability);
   Optional<Volunteer> findByUser(User user);
}

