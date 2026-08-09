package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.Customer;
import com.dfcr.workshopmanager.entity.Vehicle;
import com.dfcr.workshopmanager.exception.VehicleNotFoundExceptionById;
import com.dfcr.workshopmanager.exception.VehicleNotFoundExceptionByPlate;
import com.dfcr.workshopmanager.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return vehicleRepository.findById(id).orElseThrow(() -> new VehicleNotFoundExceptionById(id));
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

    public List<Vehicle> findByCustomerId(Long id){
        return vehicleRepository.findByCustomerId(id);
    }

    public Vehicle findByLicensePlate(String licensePlate){
        return vehicleRepository.findByLicensePlate(licensePlate).orElseThrow(() -> new VehicleNotFoundExceptionByPlate(licensePlate));
    }
}