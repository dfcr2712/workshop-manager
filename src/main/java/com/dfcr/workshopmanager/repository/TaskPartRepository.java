package com.dfcr.workshopmanager.repository;

import com.dfcr.workshopmanager.entity.Task;
import com.dfcr.workshopmanager.entity.TaskPart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskPartRepository extends JpaRepository<TaskPart, Long> {

    Task findByTaskId(Long taskId);
}
