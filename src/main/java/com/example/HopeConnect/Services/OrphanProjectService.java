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

    public Optional<OrphanProject> getProjectById(Integer id) {
        return repository.findById(id);
    }

    public OrphanProject saveProject(OrphanProject project) {
        return repository.save(project);
    }

    public void deleteProject(Integer id) {
        repository.deleteById(id);
    }
}
