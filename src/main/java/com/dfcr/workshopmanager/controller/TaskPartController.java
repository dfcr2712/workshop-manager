package com.dfcr.workshopmanager.controller;

import com.dfcr.workshopmanager.entity.TaskPart;
import com.dfcr.workshopmanager.service.TaskPartService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/task-parts")
public class TaskPartController {

    private final TaskPartService taskPartService;

    public TaskPartController(TaskPartService taskPartService) {
        this.taskPartService = taskPartService;
    }

    @PostMapping("/task/{taskId}/part/{partId}")
    public TaskPart createTaskPart(@PathVariable Long taskId, @PathVariable Long partId, @RequestParam BigDecimal quantity) {
        return taskPartService.createTaskPart(taskId, partId, quantity);
    }

    @GetMapping("/{id}")
    public TaskPart getTaskPartById(@PathVariable Long id) {
        return taskPartService.getTaskPartById(id);
    }

    @GetMapping("/task/{taskId}")
    public List<TaskPart> getTaskPartsByTaskId(@PathVariable Long taskId) {
        return taskPartService.getTaskPartsByTaskId(taskId);
    }

    @PutMapping("/{id}/quantity")
    public TaskPart updateTaskPartQuantity(@PathVariable Long id, @RequestBody BigDecimal quantity) {
        return taskPartService.updateTaskPartQuantity(id, quantity);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTaskPart(@PathVariable Long id) {
        taskPartService.deleteTaskPart(id);
    }

    @GetMapping("/task/{taskId}/material-cost")
    public BigDecimal calculateMaterialCost(@PathVariable Long taskId) {
        return taskPartService.calculateMaterialCost(taskId);
    }

}
