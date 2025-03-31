package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Models.OrphanProject;
import com.example.HopeConnect.Services.OrphanProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orphan-projects")
//@RequestMapping("orphanprojects")
public class OrphanProjectController {

    @Autowired
    private OrphanProjectService service;

    @GetMapping
    public List<OrphanProject> getAllProjects() {
        return service.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrphanProject> getProjectById(@PathVariable Integer id) {
        Optional<OrphanProject> project = service.getProjectById(id);
        return project.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public OrphanProject createProject(@RequestBody OrphanProject project) {
        return service.saveProject(project);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrphanProject> updateProject(@PathVariable Integer id, @RequestBody OrphanProject project) {
        if (!service.getProjectById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        project.setId(id);
        return ResponseEntity.ok(service.saveProject(project));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer id) {
   /**/     if (!service.getProjectById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        service.deleteProject(id);
  // System.out.print("Deleted Project");
        return ResponseEntity.noContent().build();
    }
}
