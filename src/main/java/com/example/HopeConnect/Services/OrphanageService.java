package com.example.HopeConnect.Services;

import com.example.HopeConnect.Models.Orphanage;
import com.example.HopeConnect.Enumes.OrphanageStatus;
import com.example.HopeConnect.Repositories.OrphanageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrphanageService {

    @Autowired
    private OrphanageRepository orphanageRepository;

    public List<Orphanage> getAllOrphanages() {
        return orphanageRepository.findAll();
    }

    public Optional<Orphanage> getOrphanageById(Long id) {
        return orphanageRepository.findById(id);
    }

    public List<Orphanage> getOrphanagesByCity(String city) {
        return orphanageRepository.findByCity(city);
    }

    public List<Orphanage> getOrphanagesByStatus(OrphanageStatus status) { // تم التحديث هنا
        return orphanageRepository.findByStatus(status);
    }

    public Orphanage createOrphanage(Orphanage orphanage) {
        orphanage.setStatus(OrphanageStatus.ACTIVE); // تأكد من تعيين حالة ابتدائية عند الإنشاء
        return orphanageRepository.save(orphanage);
    }

    public String deleteOrphanage(Long id) {
        if (orphanageRepository.existsById(id)) {
            orphanageRepository.deleteById(id);
            return "Orphanage deleted successfully.";
        } else {
            return "Error: Orphanage not found.";
        }
    }
}
