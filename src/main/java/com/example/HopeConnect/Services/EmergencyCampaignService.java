package com.example.HopeConnect.Services;

import com.example.HopeConnect.Models.EmergencyCampaign;
import com.example.HopeConnect.Repositories.EmergencyCampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmergencyCampaignService {

    @Autowired
    private EmergencyCampaignRepository campaignRepository;

    @Autowired
    private EmailService emailService;

    public EmergencyCampaign createCampaign(EmergencyCampaign campaign) {
        campaign.setStatus("ACTIVE");
        campaign.setCollectedAmount(0.0);
        EmergencyCampaign saved = campaignRepository.save(campaign);

        emailService.notifyUsersOfNewEmergency(saved);
        return saved;
    }

    public List<EmergencyCampaign> getActiveCampaigns() {
        return campaignRepository.findByStatus("ACTIVE");
    }

    public EmergencyCampaign donate(Long campaignId, Double amount) {
        EmergencyCampaign campaign = campaignRepository.findById(campaignId).orElseThrow();
        campaign.setCollectedAmount(campaign.getCollectedAmount() + amount);
        return campaignRepository.save(campaign);
    }
}
