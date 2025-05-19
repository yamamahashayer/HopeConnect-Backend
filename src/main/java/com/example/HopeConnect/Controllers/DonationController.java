package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Errors.ErrorResponse;
import com.example.HopeConnect.Models.Donation;
import com.example.HopeConnect.Models.Donor;
import com.example.HopeConnect.Services.DonationService;
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

    @GetMapping
    public List<Donation> getAllDonations() {
        return donationService.getAllDonations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donation> getDonationById(@PathVariable Long id) {
        Donation donation = donationService.getDonationById(id);
        return donation != null ? ResponseEntity.ok(donation) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createDonation(@RequestBody Donation donation) {
        try {
            Donation createdDonation = donationService.createDonation(donation);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDonation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error: " + e.getMessage()));
        }
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