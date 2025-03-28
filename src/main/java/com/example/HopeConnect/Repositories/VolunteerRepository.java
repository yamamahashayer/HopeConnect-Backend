package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Models.Volunteer;
import java.util.Optional;
import java.util.List;
import com.example.HopeConnect.Enumes.VolunteerStatus;
import com.example.HopeConnect.Enumes.VolunteerAvailability;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {


   List<Volunteer> findByStatus(VolunteerStatus status);
   List<Volunteer> findByAvailability(VolunteerAvailability availability);
   Optional<Volunteer> findByUser(User user);
}

