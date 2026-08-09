package com.dfcr.workshopmanager.controller;

import com.dfcr.workshopmanager.entity.ServiceOrder;
import com.dfcr.workshopmanager.enums.ServiceOrderStatus;
import com.dfcr.workshopmanager.service.ServiceOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-orders")
public class ServiceOrderController {

    private final ServiceOrderService serviceOrderService;

    public ServiceOrderController(ServiceOrderService serviceOrderService) {
        this.serviceOrderService = serviceOrderService;
    }

    @PostMapping("/vehicle/{vehicleId}")
    public ServiceOrder createServiceOrder(@PathVariable Long vehicleId, @RequestBody ServiceOrder serviceOrder) {
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
    public ServiceOrder updateServiceOrder(@PathVariable Long id, @RequestBody ServiceOrder updatedServiceOrder) {
        return serviceOrderService.updateServiceOrder(id, updatedServiceOrder);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteServiceOrder(@PathVariable Long id) {
        serviceOrderService.deleteServiceOrder(id);
    }

    @GetMapping("/vehicle/{vehicleId}")
    public List<ServiceOrder> getServiceOrdersByVehicleId(@PathVariable Long vehicleId){
        return serviceOrderService.getServiceOrdersByVehicleId(vehicleId);
    }

    @GetMapping("/status/{status}")
    public List<ServiceOrder> findByStatus(@PathVariable ServiceOrderStatus status){
        return serviceOrderService.findByStatus(status);
    }
}
