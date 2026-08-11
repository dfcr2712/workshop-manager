package com.dfcr.workshopmanager.controller;

import com.dfcr.workshopmanager.entity.Task;
import com.dfcr.workshopmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/serviceOrder/{serviceOrderId}")
    public Task createTask(@PathVariable Long serviceOrderId, @RequestBody @Valid Task task) {
        return taskService.createTask(serviceOrderId, task);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/serviceOrder/{serviceOrderId}")
    public List<Task> getTasksByServiceOrderId(@PathVariable Long serviceOrderId) {
        return taskService.getTasksByServiceOrderId(serviceOrderId);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody @Valid Task updateTask) {
        return taskService.updateTask(id, updateTask);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

}
