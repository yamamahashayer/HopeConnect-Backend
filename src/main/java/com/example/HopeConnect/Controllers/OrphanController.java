package com.example.HopeConnect.Controllers;
import com.example.HopeConnect.Models.Orphan;
import com.example.HopeConnect.Services.OrphanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orphans")
public class OrphanController {

    @Autowired
    private OrphanService orphanService;

    @GetMapping
    public List<Orphan> getAllOrphans() {
        return orphanService.getAllOrphans();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orphan> getOrphanById(@PathVariable Long id) {
        Optional<Orphan> orphan = orphanService.getOrphanById(id);
        return orphan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Orphan createOrphan(@RequestBody Orphan orphan) {
        return orphanService.saveOrphan(orphan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrphan(@PathVariable Long id) {
        orphanService.deleteOrphan(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Orphan> updateOrphan(@PathVariable Long id, @RequestBody Orphan orphan) {
        Optional<Orphan> existingOrphan = orphanService.getOrphanById(id);

        if (existingOrphan.isPresent()) {
            // تحديث الـ Orphan الموجود
            Orphan updatedOrphan = existingOrphan.get();

            updatedOrphan.setName(orphan.getName());
            updatedOrphan.setDateOfBirth(orphan.getDateOfBirth());
            updatedOrphan.setGender(orphan.getGender());
            updatedOrphan.setHealthStatus(orphan.getHealthStatus());
            updatedOrphan.setEducationStatus(orphan.getEducationStatus());
            updatedOrphan.setProfileImage(orphan.getProfileImage());
            updatedOrphan.setDescription(orphan.getDescription());
            updatedOrphan.setStatus(orphan.getStatus());
            updatedOrphan.setOrphanage(orphan.getOrphanage());

            // حفظ التحديثات
            orphanService.saveOrphan(updatedOrphan);

            return ResponseEntity.ok(updatedOrphan); // إرجاع الـ Orphan المعدل مع حالة 200
        } else {
            return ResponseEntity.notFound().build(); // إرجاع حالة 404 إذا لم يتم العثور على الـ Orphan
        }
    }

}