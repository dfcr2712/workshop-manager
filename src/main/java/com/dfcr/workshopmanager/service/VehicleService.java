package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.Customer;
import com.dfcr.workshopmanager.entity.Vehicle;
import com.dfcr.workshopmanager.exception.VehicleNotFoundException;
import com.dfcr.workshopmanager.repository.VehicleRepository;
import jakarta.validation.valueextraction.UnwrapByDefault;
import jakarta.validation.valueextraction.Unwrapping;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomerService customerService;

    public VehicleService(
            VehicleRepository vehicleRepository,
            CustomerService customerService) {
        this.vehicleRepository = vehicleRepository;
        this.customerService = customerService;
    }

    public Vehicle saveVehicle(Vehicle vehicle, Long customerId) {

        Customer customer = customerService.getCustomerById(customerId);

        vehicle.setCustomer(customer);

        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles(){
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id){
        return vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundException(id));
    }

    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle){
        Vehicle existingVehicle = getVehicleById(id);

        existingVehicle.setBrand(updatedVehicle.getBrand());
        existingVehicle.setModel(updatedVehicle.getModel());
        existingVehicle.setYear(updatedVehicle.getYear());
        existingVehicle.setLicensePlate(updatedVehicle.getLicensePlate());
        existingVehicle.setVin(updatedVehicle.getVin());

        return vehicleRepository.save(existingVehicle);
    }

    public void deleteVehicle(Long id){
        Vehicle v3 = getVehicleById(id);
        vehicleRepository.delete(v3);
    }
}