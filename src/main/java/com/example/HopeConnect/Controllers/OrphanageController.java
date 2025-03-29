package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.DTO.OrphanageDTO;
import com.example.HopeConnect.Enumes.OrphanageStatus;
import com.example.HopeConnect.Errors.ErrorResponse;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Services.OrphanageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orphanages")
public class OrphanageController {

    private final OrphanageService orphanageService;

    @Autowired
    public OrphanageController(OrphanageService orphanageService) {
        this.orphanageService = orphanageService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrphanageDTO>> getAllOrphanages() {
        List<OrphanageDTO> orphanages = orphanageService.getAllOrphanages();
        return orphanages.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.ok(orphanages);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getOrphanageById(@PathVariable Long id) {
        Optional<OrphanageDTO> orphanage = orphanageService.getOrphanageById(id);

        if (orphanage.isPresent()) {
            return ResponseEntity.ok(orphanage.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Error: Orphanage not found."));
        }
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<?> deleteOrphanage(@PathVariable Long id) {
        String result = orphanageService.deleteOrphanage(id);

        if (result.startsWith("Error")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }




    @PostMapping("/new")
    public ResponseEntity<?> createOrphanage(@RequestBody OrphanageDTO orphanageDTO, @RequestParam Long managerId) {
        try {
            OrphanageDTO savedOrphanage = orphanageService.createOrphanage(orphanageDTO, managerId);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedOrphanage);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }




    @GetMapping("/manager/{managerId}")
    public ResponseEntity<Object> getOrphanageByManager(@PathVariable Long managerId) {
        try {
            Optional<OrphanageDTO> orphanage = orphanageService.getOrphanageByManager(managerId);

            if (orphanage.isPresent()) {
                return ResponseEntity.ok(orphanage.get()); // Return OrphanageDTO if found
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Error: No orphanage found for this manager."));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Object> getOrphanagesByStatus(@PathVariable String status) {
        try {
            OrphanageStatus orphanageStatus = OrphanageStatus.valueOf(status.toUpperCase()); // Convert string to ENUM
            List<OrphanageDTO> orphanages = orphanageService.getOrphanagesByStatus(orphanageStatus);
            return orphanages.isEmpty()
                    ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ErrorResponse("No orphanages found with status: " + status))
                    : ResponseEntity.ok(orphanages);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Invalid status: " + status));
        }
    }



    @GetMapping("/email/{email}")
    public ResponseEntity<Object> getOrphanageByEmail(@PathVariable String email) {
        Optional<OrphanageDTO> orphanage = orphanageService.getOrphanageByEmail(email);

        if (orphanage.isPresent()) {
            return ResponseEntity.ok(orphanage.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("No orphanage found with email: " + email));
        }
    }







}
