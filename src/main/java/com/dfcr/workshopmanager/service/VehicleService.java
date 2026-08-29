package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.Appointment;
import com.dfcr.workshopmanager.entity.Customer;
import com.dfcr.workshopmanager.entity.ServiceOrder;
import com.dfcr.workshopmanager.entity.Vehicle;
import com.dfcr.workshopmanager.exception.VehicleNotFoundException;
import com.dfcr.workshopmanager.repository.AppointmentRepository;
import com.dfcr.workshopmanager.repository.ServiceOrderRepository;
import com.dfcr.workshopmanager.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomerService customerService;
    private final ServiceOrderRepository serviceOrderRepository;
    private final AppointmentRepository appointmentRepository;

    public VehicleService(VehicleRepository vehicleRepository, CustomerService customerService, ServiceOrderRepository serviceOrderRepository, AppointmentRepository appointmentRepository) {
        this.vehicleRepository = vehicleRepository;
        this.customerService = customerService;
        this.serviceOrderRepository = serviceOrderRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public Vehicle saveVehicle(Vehicle vehicle, Long customerId) {
        vehicle.setId(null);
        Customer customer = customerService.getCustomerById(customerId);

        vehicle.setCustomer(customer);

        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
    }

    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {
        Vehicle existingVehicle = getVehicleById(id);

        existingVehicle.setBrand(updatedVehicle.getBrand());
        existingVehicle.setModel(updatedVehicle.getModel());
        existingVehicle.setYear(updatedVehicle.getYear());
        existingVehicle.setLicensePlate(updatedVehicle.getLicensePlate());
        existingVehicle.setVin(updatedVehicle.getVin());

        return vehicleRepository.save(existingVehicle);
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicle = getVehicleById(id);
        List<ServiceOrder> existingOrders = serviceOrderRepository.findByVehicleId(id);
        List<Appointment> appointments = appointmentRepository.findByVehicleId(id);

        if (!existingOrders.isEmpty() || !appointments.isEmpty()) {
            throw new IllegalArgumentException("Vehicle with id " + id + " cannot be deleted because it has associated records.");
        }
        vehicleRepository.delete(vehicle);
    }

    public List<Vehicle> findByCustomerId(Long id) {
        return vehicleRepository.findByCustomerId(id);
    }

    public Vehicle findByLicensePlate(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate).orElseThrow(() -> new VehicleNotFoundException(licensePlate));
    }
}