package com.example.HopeConnect.Controllers;
import com.example.HopeConnect.Models.SponsorActivity;
import com.example.HopeConnect.Repositories.SponsorActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sponsor-activities")
public class SponsorActivityController {

    @Autowired
    private SponsorActivityRepository sponsorActivityRepository;

    // 1. Get all sponsor activities
    @GetMapping
    public List<SponsorActivity> getAllActivities() {
        return sponsorActivityRepository.findAll();
    }

    // 2. Get activity by ID
    @GetMapping("/{id}")
    public SponsorActivity getActivityById(@PathVariable Long id) {
        return sponsorActivityRepository.findById(id).orElse(null);
    }

    // 3. Create a new activity
    @PostMapping
    public SponsorActivity createActivity(@RequestBody SponsorActivity activity) {
        return sponsorActivityRepository.save(activity);
    }

    // 4. Update an activity
    @PutMapping("/{id}")
    public SponsorActivity updateActivity(@PathVariable Long id, @RequestBody SponsorActivity activityDetails) {
        return sponsorActivityRepository.findById(id).map(activity -> {
            activity.setActivityType(activityDetails.getActivityType());
            activity.setActivityDescription(activityDetails.getActivityDescription());
            activity.setOrphan(activityDetails.getOrphan());
            activity.setOrphanage(activityDetails.getOrphanage());
            activity.setProject(activityDetails.getProject());
            activity.setSponsor(activityDetails.getSponsor());
            return sponsorActivityRepository.save(activity);
        }).orElse(null);
    }

    // 5. Delete an activity
    @DeleteMapping("/{id}")
    public String deleteActivity(@PathVariable Long id) {
        if (sponsorActivityRepository.existsById(id)) {
            sponsorActivityRepository.deleteById(id);
            return "Activity deleted successfully.";
        } else {
            return "Activity not found.";
        }
    }

    // 6. Get activities by sponsor user ID
    @GetMapping("/by-sponsor/{userId}")
    public List<SponsorActivity> getActivitiesBySponsor(@PathVariable Long userId) {
        return sponsorActivityRepository.findBySponsorId(userId);
    }
}
