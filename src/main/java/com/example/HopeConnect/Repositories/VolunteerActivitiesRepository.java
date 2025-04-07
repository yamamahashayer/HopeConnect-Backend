package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.VolunteerActivities;
import com.example.HopeConnect.Enumes.VolunteerActivityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VolunteerActivitiesRepository extends JpaRepository<VolunteerActivities, Long> {
    List<VolunteerActivities> findByStatus(VolunteerActivityStatus status);
    List<VolunteerActivities> findByVolunteerId(Long volunteerId);
    List<VolunteerActivities> findByOrphanageId(Long orphanageId);
    List<VolunteerActivities> findByProjectId(Long projectId);


    List<VolunteerActivities> findByVolunteerIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long volunteerId, LocalDateTime endDate, LocalDateTime startDate
    );
}
