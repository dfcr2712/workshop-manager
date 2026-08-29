package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.dto.ServiceOrderCosts;
import com.dfcr.workshopmanager.entity.Mechanic;
import com.dfcr.workshopmanager.entity.ServiceOrder;
import com.dfcr.workshopmanager.entity.Task;
import com.dfcr.workshopmanager.entity.Vehicle;
import com.dfcr.workshopmanager.enums.EstimateStatus;
import com.dfcr.workshopmanager.enums.ServiceOrderPriority;
import com.dfcr.workshopmanager.enums.ServiceOrderStatus;
import com.dfcr.workshopmanager.exception.ServiceOrderClosedException;
import com.dfcr.workshopmanager.exception.ServiceOrderNotFoundException;
import com.dfcr.workshopmanager.repository.ServiceOrderRepository;
import com.dfcr.workshopmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceOrderService {

    private final ServiceOrderRepository serviceOrderRepository;
    private final VehicleService vehicleService;
    private final MechanicService mechanicService;
    private final TaskRepository taskRepository;
    private final TaskPartService taskPartService;

    public ServiceOrderService(ServiceOrderRepository serviceOrderRepository, VehicleService vehicleService, MechanicService mechanicService, TaskRepository taskRepository, TaskPartService taskPartService) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.vehicleService = vehicleService;
        this.mechanicService = mechanicService;
        this.taskRepository = taskRepository;
        this.taskPartService = taskPartService;
    }

    public ServiceOrder createServiceOrder(ServiceOrder serviceOrder, Long vehicleId) {
        serviceOrder.setId(null);
        Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
        serviceOrder.setVehicle(vehicle);
        serviceOrder.setStatus(ServiceOrderStatus.OPEN);
        serviceOrder.setCreatedAt(LocalDateTime.now());
        serviceOrder.setEstimateStatus(EstimateStatus.PENDING);
        serviceOrder.setPriority(ServiceOrderPriority.NORMAL);

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
        validateServiceOrderIsEditable(existingServiceOrder);

        existingServiceOrder.setDescription(updatedServiceOrder.getDescription());
        return serviceOrderRepository.save(existingServiceOrder);
    }

    public void deleteServiceOrder(Long id) {
        ServiceOrder serviceOrder = getServiceOrderById(id);
        List<Task> tasks = taskRepository.findByServiceOrderId(id);
        if(!tasks.isEmpty()){
            throw new IllegalArgumentException("Service Order with id " + id + " cannot be deleted because it already has tasks.");
        }

        serviceOrderRepository.delete(serviceOrder);
    }

    public List<ServiceOrder> getServiceOrdersByVehicleId(Long vehicleId) {
        vehicleService.getVehicleById(vehicleId);
        return serviceOrderRepository.findByVehicleId(vehicleId);
    }

    public List<ServiceOrder> findByStatus(ServiceOrderStatus status) {
        return serviceOrderRepository.findByStatus(status);
    }

    public List<ServiceOrder> findByCreatedAtBetween(LocalDateTime startDate,
                                                     LocalDateTime endDate) {
        if(startDate.isAfter(endDate)){
            throw new IllegalArgumentException("Start date cannot be after the end date.");
        }
        return serviceOrderRepository.findByCreatedAtBetween(startDate, endDate);
    }

    public ServiceOrder assignMechanicToServiceOrder(Long orderId, Long mechanicId) {
        ServiceOrder order = getServiceOrderById(orderId);
        validateServiceOrderIsEditable(order);

        Mechanic mechanic = mechanicService.getMechanicById(mechanicId);
        if(!mechanic.isActive()){
            throw new IllegalArgumentException("Inactive mechanic cannot be assigned to a service order.");
        }
        order.setMechanic(mechanic);

        return serviceOrderRepository.save(order);
    }

    public List<ServiceOrder> findByMechanicId(Long mechanicId) {
        return serviceOrderRepository.findByMechanicId(mechanicId);
    }

    public BigDecimal calculateLaborTotal(Long serviceOrderId) {
        ServiceOrder order = serviceOrderRepository.findById(serviceOrderId).orElseThrow(() -> new ServiceOrderNotFoundException(serviceOrderId));

        List<Task> tasks = taskRepository.findByServiceOrderId(serviceOrderId);
        BigDecimal total = BigDecimal.ZERO;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            total = total.add(task.laborCost());
        }
        return total;
    }

    public BigDecimal calculatePartsTotal(Long serviceOrderId) {
        ServiceOrder order = serviceOrderRepository.findById(serviceOrderId).orElseThrow(() -> new ServiceOrderNotFoundException(serviceOrderId));

        List<Task> tasks = taskRepository.findByServiceOrderId(serviceOrderId);

        BigDecimal total = BigDecimal.ZERO;

        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            total = total.add(taskPartService.calculateMaterialCost(task.getId()));
        }
        return total;
    }

    public ServiceOrderCosts getServiceOrderCosts(Long serviceOrderId) {

        BigDecimal laborTotal = calculateLaborTotal(serviceOrderId);
        BigDecimal partsTotal = calculatePartsTotal(serviceOrderId);
        BigDecimal total = laborTotal.add(partsTotal);

        return new ServiceOrderCosts(laborTotal, partsTotal, total);
    }

    public ServiceOrder approveEstimate(Long serviceOrderId) {
        ServiceOrder order = serviceOrderRepository.findById(serviceOrderId).orElseThrow(() -> new ServiceOrderNotFoundException(serviceOrderId));
        validateServiceOrderIsEditable(order);

        order.setEstimateStatus(EstimateStatus.APPROVED);
        return serviceOrderRepository.save(order);
    }

    public ServiceOrder rejectEstimate(Long serviceOrderId) {
        ServiceOrder order = serviceOrderRepository.findById(serviceOrderId).orElseThrow(() -> new ServiceOrderNotFoundException(serviceOrderId));
        validateServiceOrderIsEditable(order);

        order.setEstimateStatus(EstimateStatus.REJECTED);
        return serviceOrderRepository.save(order);
    }

    public List<ServiceOrder> getVehicleHistory(Long vehicleId) {
        vehicleService.getVehicleById(vehicleId);

        return serviceOrderRepository.findByVehicleIdOrderByCreatedAtDesc(vehicleId);
    }

    public List<ServiceOrder> getServiceOrdersByPriority(ServiceOrderPriority priority) {
        return serviceOrderRepository.findByPriority(priority);
    }

    public ServiceOrder updatePriority(Long serviceOrderId, ServiceOrderPriority priority) {
        ServiceOrder existingOrder = getServiceOrderById(serviceOrderId);
        validateServiceOrderIsEditable(existingOrder);

        existingOrder.setPriority(priority);
        return serviceOrderRepository.save(existingOrder);
    }

    public ServiceOrder updateExpectedCompletionAt(Long serviceOrderId, LocalDateTime expectedCompletionAt) {
        ServiceOrder existingOrder = serviceOrderRepository.findById(serviceOrderId).orElseThrow(() -> new ServiceOrderNotFoundException(serviceOrderId));
        validateServiceOrderIsEditable(existingOrder);

        existingOrder.setExpectedCompletionAt(expectedCompletionAt);
        return serviceOrderRepository.save(existingOrder);
    }

    public ServiceOrder updateStatus(Long id, ServiceOrderStatus status) {
        ServiceOrder existingOrder = getServiceOrderById(id);

        validateServiceOrderIsEditable(existingOrder);

        validateStatusTransition(existingOrder.getStatus(), status);

        if (status == ServiceOrderStatus.IN_PROGRESS && existingOrder.getStartedAt() == null) {
            existingOrder.setStartedAt(LocalDateTime.now());
        }

        if (status == ServiceOrderStatus.COMPLETED) {
            validateTasksBeforeCompletion(id);
            existingOrder.setCompletedAt(LocalDateTime.now());
        }

        existingOrder.setStatus(status);
        return serviceOrderRepository.save(existingOrder);
    }

    public ServiceOrder updateCustomerNotes(Long serviceOrderId, String customerNotes) {
        ServiceOrder existingOrder = getServiceOrderById(serviceOrderId);
        existingOrder.setCustomerNotes(customerNotes);
        return serviceOrderRepository.save(existingOrder);
    }

    public ServiceOrder updateInternalNotes(Long serviceOrderId, String internalNotes) {
        ServiceOrder existingOrder = getServiceOrderById(serviceOrderId);
        existingOrder.setInternalNotes(internalNotes);
        return serviceOrderRepository.save(existingOrder);
    }

    private void validateStatusTransition(ServiceOrderStatus currentStatus, ServiceOrderStatus newStatus) {
        if (currentStatus == ServiceOrderStatus.OPEN && newStatus != ServiceOrderStatus.IN_PROGRESS && newStatus != ServiceOrderStatus.CANCELLED) {
            throw new IllegalArgumentException("Invalid status transition from OPEN to COMPLETED.");
        }

        if (currentStatus == ServiceOrderStatus.IN_PROGRESS && newStatus != ServiceOrderStatus.COMPLETED && newStatus != ServiceOrderStatus.CANCELLED) {
            throw new IllegalArgumentException("Invalid status transition from " + currentStatus + " to " + newStatus);
        }

        if (currentStatus == ServiceOrderStatus.COMPLETED || currentStatus == ServiceOrderStatus.CANCELLED) {
            throw new IllegalArgumentException("Invalid status transition from " + currentStatus + " to " + newStatus);
        }
    }

    private void validateTasksBeforeCompletion(Long serviceOrderId) {
        List<Task> tasks = taskRepository.findByServiceOrderId(serviceOrderId);

        if (tasks.isEmpty()) {
            throw new IllegalArgumentException("Service order with id " + serviceOrderId + " dont have tasks.");
        }
    }

    private void validateServiceOrderIsEditable(ServiceOrder serviceOrder){
        ServiceOrderStatus status = serviceOrder.getStatus();
        if(status == ServiceOrderStatus.COMPLETED || status == ServiceOrderStatus.CANCELLED){
            throw new ServiceOrderClosedException(serviceOrder.getId());
        }
    }
}
