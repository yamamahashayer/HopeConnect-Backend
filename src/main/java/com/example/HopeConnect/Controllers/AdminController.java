package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private OrphanageRepository orphanageRepository;

    @Autowired
    private OrphanRepository orphanRepository;

    @Autowired
    private OrphanProjectRepository orphanProjectRepository;

    @Autowired
    private DonationRepository donationRepository;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new LinkedHashMap<>();


        stats.put("Total Projects", orphanProjectRepository.count());
        stats.put("Registered Donors", donorRepository.count());
        stats.put("Active Volunteers", volunteerRepository.count());
        stats.put("Available Orphanages", orphanageRepository.count());
        stats.put("Total Orphans", orphanRepository.count());




        return ResponseEntity.ok(stats);
    }


}
