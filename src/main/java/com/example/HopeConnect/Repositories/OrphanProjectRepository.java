package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.OrphanProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrphanProjectRepository extends JpaRepository<OrphanProject, Integer> {


}
