package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.Orphanage;
import com.example.HopeConnect.Enumes.OrphanageStatus;
import com.example.HopeConnect.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrphanageRepository extends JpaRepository<Orphanage, Long> {
    List<Orphanage> findByStatus(OrphanageStatus status);
    Optional<Orphanage> findByManager(User manager);
    Optional<Orphanage> findByEmail(String email);
}
