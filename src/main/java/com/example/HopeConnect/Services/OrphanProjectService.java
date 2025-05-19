package com.example.HopeConnect.Services;

import com.example.HopeConnect.Models.OrphanProject;
import com.example.HopeConnect.Repositories.OrphanProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrphanProjectService {

    @Autowired
    private OrphanProjectRepository repository;

    public List<OrphanProject> getAllProjects() {
        return repository.findAll();
    }

    public Optional<OrphanProject> getProjectById(Long id) {
        return repository.findById(Math.toIntExact(id));
    }

    public OrphanProject saveProject(OrphanProject project) {
        return repository.save(project);
    }

    public void deleteProject(Long id) {
        repository.deleteById(Math.toIntExact(id));
    }
    public Optional<OrphanProject> findById(Long id) {
        return repository.findById(Math.toIntExact(id));
    }
}