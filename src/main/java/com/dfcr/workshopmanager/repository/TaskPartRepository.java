package com.dfcr.workshopmanager.repository;

import com.dfcr.workshopmanager.entity.TaskPart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TaskPartRepository extends JpaRepository<TaskPart, Long> {

    List<TaskPart> findByTaskId(Long taskId);
    List<TaskPart> findByPartId(Long partId);
}
