package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.Orphanage;
import com.example.HopeConnect.Enumes.OrphanageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrphanageRepository extends JpaRepository<Orphanage, Long> {
    List<Orphanage> findByCity(String city);
    List<Orphanage> findByStatus(OrphanageStatus status); // تحديث البحث باستخدام enum الجديد
}
