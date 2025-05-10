package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
