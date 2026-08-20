package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.Part;
import com.dfcr.workshopmanager.entity.Task;
import com.dfcr.workshopmanager.entity.TaskPart;
import com.dfcr.workshopmanager.exception.InsufficientStockException;
import com.dfcr.workshopmanager.exception.PartNotFoundException;
import com.dfcr.workshopmanager.exception.TaskNotFoundException;
import com.dfcr.workshopmanager.exception.TaskPartNotFoundException;
import com.dfcr.workshopmanager.repository.PartRepository;
import com.dfcr.workshopmanager.repository.TaskPartRepository;
import com.dfcr.workshopmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // Com transactional ou corre tudo bem ou não fica nada gravado
    @Transactional
    public TaskPart createTaskPart(Long taskId, Long partId, BigDecimal quantity) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        Part part = partRepository.findById(partId).orElseThrow(() -> new PartNotFoundException("Part not found with id " + partId));

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity must be greather than zero.");
        }
        if (part.getStockQuantity().compareTo(quantity) < 0) {
            throw new InsufficientStockException(quantity);
        } else {
            BigDecimal newStock = part.getStockQuantity().subtract(quantity);
            part.setStockQuantity(newStock);
            partRepository.save(part);
        }

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

    @Transactional
    public void deleteTaskPart(Long taskPartId) {
        TaskPart taskPart = taskPartRepository.findById((taskPartId)).orElseThrow(() -> new TaskPartNotFoundException(taskPartId));

        Part part = taskPart.getPart();
        BigDecimal restoredStock = part.getStockQuantity().add(taskPart.getQuantity());

        part.setStockQuantity(restoredStock);
        partRepository.save(part);

        taskPartRepository.delete(taskPart);
    }

    public TaskPart getTaskPartById(Long taskPartId) {
        return taskPartRepository.findById(taskPartId).orElseThrow(() -> new TaskPartNotFoundException(taskPartId));
    }

    /**
     * Mantém TaskPart e stock sincronizados quando corrijo a quantidade
     * de uma peça já utilizada.
     * Se aumentar, retirar apenas a diferença ao stock, não permitindo que o aumento
     * ultrapasse o stock disponivel.
     * Se diminuir, devolver a diferença ao stock.
     */
    @Transactional
    public TaskPart updateTaskPartQuantity(Long taskPartid, BigDecimal quantity) {
        if(quantity == null || quantity.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        TaskPart existingTaskPart = getTaskPartById(taskPartid);

        BigDecimal oldQuantity = existingTaskPart.getQuantity();

        Part part = existingTaskPart.getPart();

        BigDecimal difference = quantity.subtract(oldQuantity);

        if (difference.compareTo(BigDecimal.ZERO) > 0) {
            if (part.getStockQuantity().compareTo(difference) < 0) {
                throw new InsufficientStockException(difference);
            }
            part.setStockQuantity(part.getStockQuantity().subtract(difference));
        } else if (difference.compareTo(BigDecimal.ZERO) < 0) {
            part.setStockQuantity(part.getStockQuantity().add(difference.abs()));
        }
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
