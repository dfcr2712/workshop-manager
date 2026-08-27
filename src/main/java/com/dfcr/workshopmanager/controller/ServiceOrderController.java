package com.dfcr.workshopmanager.controller;

import com.dfcr.workshopmanager.dto.ServiceOrderCosts;
import com.dfcr.workshopmanager.entity.ServiceOrder;
import com.dfcr.workshopmanager.enums.ServiceOrderPriority;
import com.dfcr.workshopmanager.enums.ServiceOrderStatus;
import com.dfcr.workshopmanager.service.ServiceOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/service-orders")
public class ServiceOrderController {

    private final ServiceOrderService serviceOrderService;

    public ServiceOrderController(ServiceOrderService serviceOrderService) {
        this.serviceOrderService = serviceOrderService;
    }

    @PostMapping("/vehicle/{vehicleId}")
    public ServiceOrder createServiceOrder(@PathVariable Long vehicleId, @RequestBody @Valid ServiceOrder serviceOrder) {
        return serviceOrderService.createServiceOrder(serviceOrder, vehicleId);
    }

    @GetMapping
    public List<ServiceOrder> getAllServiceOrders() {
        return serviceOrderService.getAllServiceOrders();
    }

    @GetMapping("/{id}")
    public ServiceOrder getServiceById(@PathVariable Long id) {
        return serviceOrderService.getServiceOrderById(id);
    }

    @PutMapping("/{id}")
    public ServiceOrder updateServiceOrder(@PathVariable Long id, @RequestBody @Valid ServiceOrder updatedServiceOrder) {
        return serviceOrderService.updateServiceOrder(id, updatedServiceOrder);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteServiceOrder(@PathVariable Long id) {
        serviceOrderService.deleteServiceOrder(id);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public List<ServiceOrder> getServiceOrdersByVehicleId(@PathVariable Long vehicleId) {
        return serviceOrderService.getServiceOrdersByVehicleId(vehicleId);
    }

    @GetMapping("/status/{status}")
    public List<ServiceOrder> findByStatus(@PathVariable ServiceOrderStatus status) {
        return serviceOrderService.findByStatus(status);
    }

    @GetMapping("/dates/{startDate}/{endDate}")
    public List<ServiceOrder> findByCreatedAtBetween(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(LocalTime.MAX);
        return serviceOrderService.findByCreatedAtBetween(start, end);
    }

    @PutMapping("/{orderId}/mechanic/{mechanicId}")
    public ServiceOrder assignMechanicToServiceOrder(@PathVariable Long orderId, @PathVariable Long mechanicId) {
        return serviceOrderService.assignMechanicToServiceOrder(orderId, mechanicId);
    }

    @GetMapping("/mechanic/{mechanicId}")
    public List<ServiceOrder> mechanicOrders(@PathVariable Long mechanicId) {
        return serviceOrderService.findByMechanicId(mechanicId);
    }

    @GetMapping("/{id}/costs")
    public ServiceOrderCosts getServiceOrderCosts(@PathVariable Long id) {
        return serviceOrderService.getServiceOrderCosts(id);
    }

    @PutMapping("/{id}/estimate/approve")
    public ServiceOrder approveEstimate(@PathVariable Long id) {
        return serviceOrderService.approveEstimate(id);
    }

    @PutMapping("/{id}/estimate/reject")
    public ServiceOrder rejectEstimate(@PathVariable Long id) {
        return serviceOrderService.rejectEstimate(id);
    }

    @GetMapping("/priority/{priority}")
    public List<ServiceOrder> getServiceOrdersByPriority(@PathVariable ServiceOrderPriority priority) {
        return serviceOrderService.getServiceOrdersByPriority(priority);
    }

    @PutMapping("/{id}/priority/{priority}")
    public ServiceOrder updatePriority(@PathVariable Long id, @PathVariable ServiceOrderPriority priority) {
        return serviceOrderService.updatePriority(id, priority);
    }

    @PutMapping("/{id}/expected-completion")
    public ServiceOrder updateExpectedCompletionAt(
            @PathVariable Long id,
            @RequestBody LocalDateTime expectedCompletionAt){
        return serviceOrderService.updateExpectedCompletionAt(id, expectedCompletionAt);
    }

    @PutMapping("/{id}/status/{status}")
    public ServiceOrder updateStatus(@PathVariable Long id,
                                     @PathVariable ServiceOrderStatus status){
        return serviceOrderService.updateStatus(id, status);
    }
}
