package com.example.HopeConnect.Controllers;
import com.example.HopeConnect.Models.Task;
import com.example.HopeConnect.Repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @PostMapping
    public Task create(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @PutMapping("/{id}")
    public Task update(@PathVariable Long id, @RequestBody Task updatedTask) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();
            existingTask.setPickupLat(updatedTask.getPickupLat());
            existingTask.setPickupLng(updatedTask.getPickupLng());
            existingTask.setDropoffLat(updatedTask.getDropoffLat());
            existingTask.setDropoffLng(updatedTask.getDropoffLng());
            existingTask.setStatus(updatedTask.getStatus());
            existingTask.setScheduledTime(updatedTask.getScheduledTime());
            existingTask.setVolunteerId(updatedTask.getVolunteerId());
            existingTask.setDonation(updatedTask.getDonation());

            return taskRepository.save(existingTask);
        } else {
            throw new RuntimeException("Task not found with id " + id);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}
