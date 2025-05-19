
package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Models.OrphanProject;
import com.example.HopeConnect.Services.OrphanProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orphanprojects")

public class OrphanProjectController {

    @Autowired
    private OrphanProjectService service;

    @GetMapping
    public List<OrphanProject> getAllProjects() {
        return service.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrphanProject> getProjectById(@PathVariable Long id) {
        Optional<OrphanProject> project = service.getProjectById(id);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProject(@RequestBody OrphanProject project) {
        return ResponseEntity.ok(service.saveProject(project));
    }
    @PutMapping("/{id}")
    public ResponseEntity<OrphanProject> updateProject(@PathVariable Long id, @RequestBody OrphanProject project) {
        if (!service.getProjectById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.saveProject(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {

        if (!service.getProjectById(id).isPresent()) {

            return ResponseEntity.notFound().build();
        }
        service.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}