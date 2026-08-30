package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.ServiceOrder;
import com.dfcr.workshopmanager.entity.Task;
import com.dfcr.workshopmanager.entity.TaskPart;
import com.dfcr.workshopmanager.enums.ServiceOrderStatus;
import com.dfcr.workshopmanager.exception.ServiceOrderClosedException;
import com.dfcr.workshopmanager.exception.TaskNotFoundException;
import com.dfcr.workshopmanager.repository.TaskPartRepository;
import com.dfcr.workshopmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ServiceOrderService serviceOrderService;
    private final TaskPartRepository taskPartRepository;


    public TaskService(TaskRepository taskRepository, ServiceOrderService serviceOrderService, TaskPartRepository taskPartRepository) {
        this.taskRepository = taskRepository;
        this.serviceOrderService = serviceOrderService;
        this.taskPartRepository = taskPartRepository;
    }

    public Task createTask(Long serviceOrderId, Task task){
        task.setId(null);
        ServiceOrder order = serviceOrderService.getServiceOrderById(serviceOrderId);
        validateServiceOrderIsEditable(order);
        task.setServiceOrder(order);
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<Task> getTasksByServiceOrderId(Long serviceOrderId){
        serviceOrderService.getServiceOrderById(serviceOrderId);
        return taskRepository.findByServiceOrderId(serviceOrderId);
    }

    public Task updateTask(Long id, Task updateTask){
        Task existingTask = getTaskById(id);
        validateServiceOrderIsEditable(existingTask.getServiceOrder());

        existingTask.setDescription(updateTask.getDescription());
        existingTask.setLaborHours(updateTask.getLaborHours());
        existingTask.setHourlyRate(updateTask.getHourlyRate());

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id){
        Task task = getTaskById(id);
        validateServiceOrderIsEditable(task.getServiceOrder());

        List<TaskPart> taskParts = taskPartRepository.findByTaskId(id);
        if(!taskParts.isEmpty()){
            throw new IllegalArgumentException("Task with id " + id + " cannot be deleted because it already has parts.");
        }
        taskRepository.delete(task);
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    private void validateServiceOrderIsEditable(ServiceOrder serviceOrder){
        ServiceOrderStatus status = serviceOrder.getStatus();
        if(status == ServiceOrderStatus.COMPLETED ||
        status == ServiceOrderStatus.CANCELLED){
            throw new ServiceOrderClosedException(serviceOrder.getId());
        }
    }
}
