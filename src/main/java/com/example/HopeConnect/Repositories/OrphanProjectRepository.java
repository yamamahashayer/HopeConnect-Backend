package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.OrphanProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrphanProjectRepository extends JpaRepository<OrphanProject, Integer> {
}
