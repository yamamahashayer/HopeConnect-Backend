package com.example.HopeConnect.Services;

import com.example.HopeConnect.Repositories.OrphanRepository;
import com.example.HopeConnect.Repositories.VolunteerRepository;
import com.example.HopeConnect.Repositories.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private OrphanRepository orphanRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private DonationRepository donationRepository;

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        stats.put("totalOrphans", orphanRepository.count());
        stats.put("totalVolunteers", volunteerRepository.count());
        stats.put("totalDonations", donationRepository.count());
        stats.put("totalDonationAmount", donationRepository.sumTotalAmount());

        return stats;
    }
}
