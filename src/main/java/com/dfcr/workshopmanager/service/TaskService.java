package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.ServiceOrder;
import com.dfcr.workshopmanager.entity.Task;
import com.dfcr.workshopmanager.exception.TaskServiceNotFoundException;
import com.dfcr.workshopmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ServiceOrderService serviceOrderService;


    public TaskService(TaskRepository taskRepository, ServiceOrderService serviceOrderService) {
        this.taskRepository = taskRepository;
        this.serviceOrderService = serviceOrderService;
    }

    public Task createTask(Long serviceOrderId, Task task){
        ServiceOrder order = serviceOrderService.getServiceOrderById(serviceOrderId);
        task.setServiceOrder(order);
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(() -> new TaskServiceNotFoundException(id));
    }

    public List<Task> getTasksByServiceOrderId(Long serviceOrderId){
        return taskRepository.findByServiceOrderId(serviceOrderId);
    }

    public Task updateTask(Long id, Task updateTask){
        Task existingTask = getTaskById(id);

        existingTask.setDescription(updateTask.getDescription());
        existingTask.setLaborHours(updateTask.getLaborHours());
        existingTask.setHourlyRate(updateTask.getHourlyRate());
        existingTask.setMaterialCost(updateTask.getMaterialCost());

        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id){
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
}
