package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Models.Donation;
import com.example.HopeConnect.Models.Donor;
import com.example.HopeConnect.Services.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/donations")
public class DonationController {
    @Autowired
    private DonationService donationService;

    @GetMapping
    public List<Donation> getAllDonations() {
        return donationService.getAllDonations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable Long id) {
        Donation donation = donationService.getDonationById(id);
        return donation != null ? ResponseEntity.ok(donation) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Donation createDonation(@RequestBody Donation donation) {
        return donationService.createDonation(donation);
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
