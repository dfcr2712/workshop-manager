package com.dfcr.workshopmanager.repository;

import com.dfcr.workshopmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByServiceOrderId(Long serviceOrderId);
}
