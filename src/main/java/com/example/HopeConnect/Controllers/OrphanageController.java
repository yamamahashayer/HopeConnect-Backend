package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Models.Orphanage;
import com.example.HopeConnect.Enumes.OrphanageStatus;
import com.example.HopeConnect.Services.OrphanageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orphanages")
public class OrphanageController {

    @Autowired
    private OrphanageService orphanageService;

    @GetMapping("/all")
    public ResponseEntity<List<Orphanage>> getAllOrphanages() {
        List<Orphanage> orphanages = orphanageService.getAllOrphanages();
        return orphanages.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(orphanages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orphanage> getOrphanageById(@PathVariable Long id) {
        Orphanage orphanage = orphanageService.getOrphanageById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orphanage not found"));
        return ResponseEntity.ok(orphanage);
    }



    @GetMapping("/status")
    public ResponseEntity<?> getOrphanagesByStatus(@RequestParam String status) {
        try {
            OrphanageStatus orphanageStatus = OrphanageStatus.valueOf(status.toUpperCase());
            List<Orphanage> orphanages = orphanageService.getOrphanagesByStatus(orphanageStatus);
            return orphanages.isEmpty()
                    ? ResponseEntity.status(HttpStatus.NO_CONTENT).body("No orphanages found with status: " + status)
                    : ResponseEntity.ok(orphanages);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid status value: " + status);
        }
    }

    @PostMapping("/new")
    public ResponseEntity<?> createOrphanage(@RequestBody Orphanage orphanage) {
        Orphanage savedOrphanage = orphanageService.createOrphanage(orphanage);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrphanage);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteOrphanage(@PathVariable Long id) {
        String response = orphanageService.deleteOrphanage(id);
        return response.contains("Error") ? ResponseEntity.status(404).body(response) : ResponseEntity.ok(response);
    }
}
