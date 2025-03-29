package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.DTO.OrphanageDTO;
import com.example.HopeConnect.Errors.ErrorResponse;
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

    /** ✅ Get all orphanages **/
    @GetMapping("/all")
    public ResponseEntity<List<OrphanageDTO>> getAllOrphanages() {
        List<OrphanageDTO> orphanages = orphanageService.getAllOrphanages();
        return orphanages.isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.ok(orphanages);
    }

    /** ✅ Get orphanage by ID **/
    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrphanageById(@PathVariable Long id) {
        Optional<OrphanageDTO> orphanage = orphanageService.getOrphanageById(id);

        if (orphanage.isPresent()) {
            return ResponseEntity.ok(orphanage.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Error: Orphanage not found."));
        }
    }





    /** ✅ Create new orphanage **/
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
}
