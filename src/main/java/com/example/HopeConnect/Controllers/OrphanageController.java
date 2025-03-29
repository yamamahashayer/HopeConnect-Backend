package com.example.HopeConnect.Controllers;
import com.example.HopeConnect.DTO.OrphanageDTO;
import com.example.HopeConnect.Services.OrphanageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orphanages")
public class OrphanageController {

    private final OrphanageService orphanageService;

    public OrphanageController(OrphanageService orphanageService) {
        this.orphanageService = orphanageService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrphanageDTO>> getAllOrphanages() {
        List<OrphanageDTO> orphanages = orphanageService.getAllOrphanages();
        return orphanages.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(orphanages);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getOrphanageById(@PathVariable Long id) {
        Optional<OrphanageDTO> orphanage = orphanageService.getOrphanageById(id);
        return orphanage.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/new")
    public ResponseEntity<?> createOrphanage(@RequestBody OrphanageDTO orphanageDTO, @RequestParam Long managerId) {
        try {
            OrphanageDTO savedOrphanage = orphanageService.createOrphanage(orphanageDTO, managerId);
            return ResponseEntity.status(201).body(savedOrphanage);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
