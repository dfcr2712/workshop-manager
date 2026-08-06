package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.ServiceOrder;
import com.dfcr.workshopmanager.entity.Vehicle;
import com.dfcr.workshopmanager.enums.ServiceOrderStatus;
import com.dfcr.workshopmanager.exception.ServiceOrderNotFoundException;
import com.dfcr.workshopmanager.repository.ServiceOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceOrderService {

    private final ServiceOrderRepository serviceOrderRepository;
    private final VehicleService vehicleService;

    public ServiceOrderService(ServiceOrderRepository serviceOrderRepository, VehicleService vehicleService) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.vehicleService = vehicleService;
    }

    public ServiceOrder createServiceOrder(ServiceOrder serviceOrder, Long vehicleId){

        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        serviceOrder.setVehicle(vehicle);
        serviceOrder.setStatus(ServiceOrderStatus.OPEN);
        serviceOrder.setCreatedAt(LocalDateTime.now());

        return serviceOrderRepository.save(serviceOrder);
    }

    public List<ServiceOrder> getAllServiceOrders(){
        return serviceOrderRepository.findAll();
    }

    public ServiceOrder getServiceOrderById(Long id){
        return serviceOrderRepository.findById(id).orElseThrow(() -> new ServiceOrderNotFoundException(id));
    }

    public ServiceOrder updateServiceOrder(Long id, ServiceOrder updatedServiceOrder){
        ServiceOrder existingServiceOrder = getServiceOrderById(id);
        if(updatedServiceOrder.getStatus() == ServiceOrderStatus.COMPLETED){
            existingServiceOrder.setCompletedAt(LocalDateTime.now());
        }

        existingServiceOrder.setStatus(updatedServiceOrder.getStatus());
        existingServiceOrder.setDescription(updatedServiceOrder.getDescription());
        return serviceOrderRepository.save(existingServiceOrder);
    }

    public void deleteServiceOrder(Long id){
        ServiceOrder serviceOrder = getServiceOrderById(id);
        serviceOrderRepository.delete(serviceOrder);
    }
}
