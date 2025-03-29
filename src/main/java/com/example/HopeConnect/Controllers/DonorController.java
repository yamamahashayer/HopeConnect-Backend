package com.example.HopeConnect.Controllers;


import com.example.HopeConnect.Models.Donor;
import com.example.HopeConnect.Services.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/donors")
public class DonorController {
    @Autowired
    private DonorService donorService;

    @GetMapping
    public List<Donor> getAllDonors() {
        return donorService.getAllDonors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Donor> getDonorById(@PathVariable Long id) {
        Donor donor = donorService.getDonorById(id);
        return donor != null ? ResponseEntity.ok(donor) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Donor createDonor(@RequestBody Donor donor) {
        return donorService.createDonor(donor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Donor> updateDonor(@PathVariable Long id, @RequestBody Donor updatedDonor) {
        Donor donor = donorService.updateDonor(id, updatedDonor);
        return donor != null ? ResponseEntity.ok(donor) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDonor(@PathVariable Long id) {
        boolean deleted = donorService.deleteDonor(id);
        if (deleted) {
            return ResponseEntity.ok("Donor is deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
       // return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();

    }
}
