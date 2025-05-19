package com.example.HopeConnect.Services;
import com.example.HopeConnect.Models.SponsorActivity;
import com.example.HopeConnect.Repositories.SponsorActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SponsorActivitiesService {

    @Autowired
    private SponsorActivityRepository sponsorActivityRepository;
    public List<SponsorActivity> getActivitiesBySponsorId(Long sponsorId) {
        return sponsorActivityRepository.findBySponsorId(sponsorId);
    }
}