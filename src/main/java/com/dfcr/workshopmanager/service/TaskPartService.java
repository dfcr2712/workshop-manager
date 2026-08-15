package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.Part;
import com.dfcr.workshopmanager.entity.Task;
import com.dfcr.workshopmanager.entity.TaskPart;
import com.dfcr.workshopmanager.exception.PartNotFoundException;
import com.dfcr.workshopmanager.exception.TaskNotFoundException;
import com.dfcr.workshopmanager.exception.TaskPartNotFoundException;
import com.dfcr.workshopmanager.repository.PartRepository;
import com.dfcr.workshopmanager.repository.TaskPartRepository;
import com.dfcr.workshopmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TaskPartService {

    private final TaskRepository taskRepository;
    private final TaskPartRepository taskPartRepository;
    private final PartRepository partRepository;

    public TaskPartService(TaskRepository taskRepository, TaskPartRepository taskPartRepository, PartRepository partRepository) {
        this.taskRepository = taskRepository;
        this.taskPartRepository = taskPartRepository;
        this.partRepository = partRepository;

    }

    public TaskPart createTaskPart(Long taskId, Long partId, BigDecimal quantity) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        Part part = partRepository.findById(partId).orElseThrow(() -> new PartNotFoundException(partId));

        TaskPart taskPart = new TaskPart();
        taskPart.setTask(task);
        taskPart.setPart(part);
        taskPart.setQuantity(quantity);

        taskPart.setUnitPrice(part.getUnitPrice());

        return taskPartRepository.save(taskPart);
    }

    public List<TaskPart> getTaskPartsByTaskId(Long taskId) {
        return taskPartRepository.findByTaskId(taskId);
    }

    public void deleteTaskPart(Long taskPartId) {
        TaskPart taskPart = taskPartRepository.findById((taskPartId)).orElseThrow(() -> new TaskPartNotFoundException(taskPartId));
        taskPartRepository.delete(taskPart);
    }

    public TaskPart getTaskPartById(Long taskPartId) {
        return taskPartRepository.findById(taskPartId).orElseThrow(() -> new TaskPartNotFoundException(taskPartId));
    }

    public TaskPart updateTaskPartQuantity(Long taskPartid, BigDecimal quantity) {
        TaskPart existingTaskPart = getTaskPartById(taskPartid);
        existingTaskPart.setQuantity(quantity);
        return taskPartRepository.save(existingTaskPart);
    }

    public BigDecimal calculateMaterialCost(Long taskId) {
        BigDecimal total = BigDecimal.ZERO;
        List<TaskPart> taskParts = getTaskPartsByTaskId(taskId);
        for (int i = 0; i < taskParts.size(); i++) {
            TaskPart newTaskPart = taskParts.get(i);

            total = total.add(newTaskPart.partTotal());
        }
        return total;
    }
}
