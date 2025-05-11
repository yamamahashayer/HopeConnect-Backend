package com.example.HopeConnect.Controllers;
/*
import com.example.HopeConnect.Models.EmergencyCampaign;
import com.example.HopeConnect.Services.EmergencyCampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emergency")
public class EmergencyCampaignController {

    @Autowired
    private EmergencyCampaignService campaignService;

    @PostMapping
    public ResponseEntity<EmergencyCampaign> create(@RequestBody EmergencyCampaign campaign) {
        return ResponseEntity.ok(campaignService.createCampaign(campaign));
    }

    @GetMapping("/active")
    public ResponseEntity<List<EmergencyCampaign>> getActiveCampaigns() {
        return ResponseEntity.ok(campaignService.getActiveCampaigns());
    }

    @PostMapping("/{id}/donate")
    public ResponseEntity<EmergencyCampaign> donate(@PathVariable Long id, @RequestParam Double amount) {
        return ResponseEntity.ok(campaignService.donate(id, amount));
    }
}
*/


import com.example.HopeConnect.Models.EmergencyCampaign;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Repositories.EmergencyCampaignRepository;
import com.example.HopeConnect.Repositories.UserRepository;
import com.example.HopeConnect.Services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/emergency")
public class EmergencyCampaignController {

    @Autowired
    private EmergencyCampaignRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;



    @PostMapping
    public EmergencyCampaign createCampaign(@RequestBody EmergencyCampaign campaign) throws IOException {
        EmergencyCampaign saved = repository.save(campaign);
       // return repository.save(campaign);
        List<com.example.HopeConnect.Models.User> donors = userRepository.findByRole("DONOR");

        String subject = "🚨 Emergency Campaign 🚨 : " + campaign.getTitle();
        String body = "A new emergency campaign has been created:\n\n"
                + "Title: " + campaign.getTitle() + "\n"
                + "Description: " + campaign.getDescription() + "\n"
                + "Goal: $" + campaign.getGoalAmount() + "\n\n"
                + "Please help if you can.";


        for (User donor : donors) {
      //  String targetEmail = "yarayousef900@gmail.com";
            emailService.sendEmail(donor.getEmail(), subject, body);
      //  emailService.sendEmail(targetEmail, subject, body);
        }

        return saved;
    }

    @GetMapping("/active")
    public List<EmergencyCampaign> getActiveCampaigns() {
        return repository.findByStatus("ACTIVE");
    }

    @PostMapping("/{id}/donate")
    public EmergencyCampaign donateToCampaign(@PathVariable Long id, @RequestParam Double amount) {
        EmergencyCampaign campaign = repository.findById(id).orElseThrow();
        campaign.setCollectedAmount(campaign.getCollectedAmount() + amount);
        return repository.save(campaign);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCampaign(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Campaign not found.");
        }
        repository.deleteById(id);
        return ResponseEntity.ok("Campaign deleted successfully.");
    }

}
