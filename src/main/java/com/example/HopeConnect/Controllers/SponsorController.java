package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Models.Sponsor;
import com.example.HopeConnect.Services.SponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/sponsors")
public class SponsorController {

    @Autowired
    private SponsorService sponsorService;

    @GetMapping
    public List<Sponsor> getAllSponsors() {
        return sponsorService.getAllSponsors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sponsor> getSponsorById(@PathVariable Long id) {
        return sponsorService.getSponsorById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Sponsor> getSponsorByUserId(@PathVariable Long userId) {
        return sponsorService.getSponsorByUserId(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Sponsor> createSponsor(@RequestBody Sponsor sponsor) {
        Sponsor savedSponsor = sponsorService.saveSponsor(sponsor);
        return ResponseEntity.ok(savedSponsor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSponsor(@PathVariable Long id) {
        sponsorService.deleteSponsor(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Sponsor> updateSponsor(@PathVariable Long id, @RequestBody Sponsor sponsorDetails) {
        return sponsorService.getSponsorById(id)
                .map(sponsor -> {
                    sponsor.setTotalSponsoredOrphans(sponsorDetails.getTotalSponsoredOrphans());
                    sponsor.setTotalDonations(sponsorDetails.getTotalDonations());
                    sponsor.setSponsorshipStartDate(sponsorDetails.getSponsorshipStartDate());
                    sponsor.setSponsorStatus(sponsorDetails.getSponsorStatus());
                    Sponsor updatedSponsor = sponsorService.saveSponsor(sponsor);  // Save updated sponsor
                    return ResponseEntity.ok(updatedSponsor);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
