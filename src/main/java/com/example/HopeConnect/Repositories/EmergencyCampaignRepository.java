package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.EmergencyCampaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmergencyCampaignRepository extends JpaRepository<EmergencyCampaign, Long> {
    List<EmergencyCampaign> findByStatus(String status);
}

