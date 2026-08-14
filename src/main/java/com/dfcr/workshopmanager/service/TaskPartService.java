package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.Part;
import com.dfcr.workshopmanager.entity.Task;
import com.dfcr.workshopmanager.entity.TaskPart;
import com.dfcr.workshopmanager.exception.PartNotFoundException;
import com.dfcr.workshopmanager.exception.TaskNotFoundException;
import com.dfcr.workshopmanager.repository.PartRepository;
import com.dfcr.workshopmanager.repository.TaskPartRepository;
import com.dfcr.workshopmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TaskPartService {

    private final TaskRepository taskRepository;
    private final TaskPartRepository taskPartRepository;
    private final PartRepository partRepository;

    public TaskPartService(TaskRepository taskRepository,TaskPartRepository taskPartRepository, PartRepository partRepository) {
        this.taskRepository = taskRepository;
        this.taskPartRepository = taskPartRepository;
        this.partRepository = partRepository;

    }

    public TaskPart createTaskPart(Long taskId, Long partId, BigDecimal quantity){
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        Part part = partRepository.findById(partId).orElseThrow(() -> new PartNotFoundException(partId));

        TaskPart taskPart = new TaskPart();
        taskPart.setTask(task);
        taskPart.setPart(part);
        taskPart.setQuantity(quantity);

        taskPart.setUnitPrice(part.getUnitPrice());

        return taskPartRepository.save(taskPart);
    }
}
