package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.Entity.Volunteer;
import java.util.Optional;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {


   Optional<Volunteer> findByEmail(String email);
   boolean existsByEmail(String email);
   List<Volunteer> findByStatus(Volunteer.Status status);
   List<Volunteer> findByCity(String city);
   List<Volunteer> findByAvailability(Volunteer.Availability availability);
   List<Volunteer> findByStatusAndCity(Volunteer.Status status, String city);
   List<Volunteer> findByStatusAndAvailability(Volunteer.Status status, Volunteer.Availability availability);
}

