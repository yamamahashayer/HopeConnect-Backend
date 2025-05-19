package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.DTO.DonationDTO;
import com.example.HopeConnect.Errors.ErrorResponse;
import com.example.HopeConnect.Models.Donation;
import com.example.HopeConnect.Models.Donor;
import com.example.HopeConnect.Models.EmergencyCampaign;
import com.example.HopeConnect.Services.DonationService;
import com.example.HopeConnect.Repositories.DonorRepository;
import com.example.HopeConnect.Repositories.DonationRepository;
import com.example.HopeConnect.Repositories.EmergencyCampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/donations")
public class DonationController {
    @Autowired
    private DonationService donationService;
    @Autowired
    private DonorRepository donorRepository;
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private EmergencyCampaignRepository emergencyCampaignRepository;
    @GetMapping
    public List<Donation> getAllDonations() {
        return donationService.getAllDonations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable Long id) {
        Donation donation = donationService.getDonationById(id);
        return donation != null ? ResponseEntity.ok(donation) : ResponseEntity.notFound().build();
    }

    /*@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDonation(@RequestBody Donation donation) {
        try {
            Donation createdDonation = donationService.createDonation(donation);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDonation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }*/
    @PostMapping
    public ResponseEntity<Donation> createDonation(@RequestBody DonationDTO dto) {
        Donation donation = new Donation();

        Donor donor = donorRepository.findById(dto.getDonorId())
                .orElseThrow(() -> new RuntimeException("Donor not found"));
        donation.setDonor(donor);

        if (dto.getOrphanId() != null) {
            donation.setOrphanId(dto.getOrphanId());
        }

        if (dto.getProjectId() != null) {
            donation.setProjectId(dto.getProjectId());
        }

        if (dto.getEmergencyCampaignId() != null) {
            EmergencyCampaign campaign = emergencyCampaignRepository.findById(dto.getEmergencyCampaignId())
                    .orElseThrow(() -> new RuntimeException("Campaign not found"));
            donation.setEmergencyCampaign(campaign);
        }

        donation.setAmount(dto.getAmount());
        donation.setCurrency(dto.getCurrency());
        // donation.setDonationType(dto.getDonationType());
        donation.setDonationType(Donation.DonationType.valueOf(dto.getDonationType().toUpperCase()));

        //donation.setPaymentStatus(dto.getPaymentStatus());
        donation.setPaymentStatus(Donation.PaymentStatus.valueOf(dto.getPaymentStatus().toUpperCase()));

        donation.setDonationDate(dto.getDonationDate());

        Donation savedDonation = donationRepository.save(donation);
        return ResponseEntity.ok(savedDonation);
    }




    @PutMapping("/{id}")
    public ResponseEntity<Donation> updateDonation(@PathVariable Long id, @RequestBody Donation updatedDonation) {
        Donation donation = donationService.updateDonation(id, updatedDonation);
        return donation != null ? ResponseEntity.ok(donation) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDonation(@PathVariable Long id) {
        boolean deleted = donationService.deleteDonation(id);
        return deleted ? ResponseEntity.ok("Donation deleted successfully") : ResponseEntity.notFound().build();
    }
}