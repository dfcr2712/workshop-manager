package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.Mechanic;
import com.dfcr.workshopmanager.entity.ServiceOrder;
import com.dfcr.workshopmanager.entity.Vehicle;
import com.dfcr.workshopmanager.enums.ServiceOrderStatus;
import com.dfcr.workshopmanager.exception.ServiceOrderClosedException;
import com.dfcr.workshopmanager.exception.ServiceOrderNotFoundException;
import com.dfcr.workshopmanager.repository.MechanicRepository;
import com.dfcr.workshopmanager.repository.ServiceOrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceOrderService {

    private final ServiceOrderRepository serviceOrderRepository;
    private final VehicleService vehicleService;
    private final MechanicService mechanicService;

    public ServiceOrderService(ServiceOrderRepository serviceOrderRepository, VehicleService vehicleService, MechanicService mechanicService) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.vehicleService = vehicleService;
        this.mechanicService = mechanicService;
    }

    public ServiceOrder createServiceOrder(ServiceOrder serviceOrder, Long vehicleId) {

        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        serviceOrder.setVehicle(vehicle);
        serviceOrder.setStatus(ServiceOrderStatus.OPEN);
        serviceOrder.setCreatedAt(LocalDateTime.now());

        return serviceOrderRepository.save(serviceOrder);
    }

    public List<ServiceOrder> getAllServiceOrders() {
        return serviceOrderRepository.findAll();
    }

    public ServiceOrder getServiceOrderById(Long id) {
        return serviceOrderRepository.findById(id).orElseThrow(() -> new ServiceOrderNotFoundException(id));
    }

    public ServiceOrder updateServiceOrder(Long id, ServiceOrder updatedServiceOrder) {
        ServiceOrder existingServiceOrder = getServiceOrderById(id);
        if (existingServiceOrder.getStatus() == ServiceOrderStatus.COMPLETED || existingServiceOrder.getStatus() == ServiceOrderStatus.CANCELLED) {
            throw new ServiceOrderClosedException(id);
        }

        if (updatedServiceOrder.getStatus() == ServiceOrderStatus.COMPLETED) {
            existingServiceOrder.setCompletedAt(LocalDateTime.now());
        }

        existingServiceOrder.setStatus(updatedServiceOrder.getStatus());
        existingServiceOrder.setDescription(updatedServiceOrder.getDescription());
        return serviceOrderRepository.save(existingServiceOrder);
    }

    public void deleteServiceOrder(Long id) {
        ServiceOrder serviceOrder = getServiceOrderById(id);
        serviceOrderRepository.delete(serviceOrder);
    }

    public List<ServiceOrder> getServiceOrdersByVehicleId(Long vehicleId) {
        vehicleService.getVehicleById(vehicleId);
        return serviceOrderRepository.findByVehicleId(vehicleId);
    }

    public List<ServiceOrder> findByStatus(ServiceOrderStatus status) {
        return serviceOrderRepository.findByStatus(status);
    }

    public List<ServiceOrder> findByCreatedAtBetween(LocalDateTime starDate, LocalDateTime endDate) {
        return serviceOrderRepository.findByCreatedAtBetween(starDate, endDate);

    }

    public ServiceOrder assignMechanicToServiceOrder(Long orderId, Long mechanicId) {
        ServiceOrder order = getServiceOrderById(orderId);
        Mechanic mechanic = mechanicService.getMechanicById(mechanicId);

        order.setMechanic(mechanic);

        return serviceOrderRepository.save(order);
    }

    public List<ServiceOrder> findByMechanicId(Long mechanicId){
        return serviceOrderRepository.findByMechanicId(mechanicId);
    }
}
