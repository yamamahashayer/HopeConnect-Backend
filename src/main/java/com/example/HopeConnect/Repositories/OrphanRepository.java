package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.Orphan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrphanRepository extends JpaRepository<Orphan, Long> {
    List<Orphan> findByStatus(String status);
}