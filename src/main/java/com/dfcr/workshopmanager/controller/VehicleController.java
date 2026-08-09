package com.dfcr.workshopmanager.controller;

import com.dfcr.workshopmanager.entity.Vehicle;
import com.dfcr.workshopmanager.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/customer/{customerId}")
    public Vehicle createVehicle(@PathVariable Long customerId, @RequestBody Vehicle vehicle) {
        return vehicleService.saveVehicle(vehicle, customerId);
    }

    @GetMapping
    public List<Vehicle> getAllVehicles(){
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{id}")
    public Vehicle getVehicleById(@PathVariable Long id){
        return vehicleService.getVehicleById(id);
    }

    @PutMapping("/{id}")
    public Vehicle updateVehicle(@PathVariable Long id, @RequestBody Vehicle updatedVehicle){
        return vehicleService.updateVehicle(id, updatedVehicle);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable Long id){
        vehicleService.deleteVehicle(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<Vehicle> findByCustomerId(@PathVariable Long customerId){
        return vehicleService.findByCustomerId(customerId);
    }

    @GetMapping("/license-plate/{licensePlate}")
    public Vehicle findByLicensePlate(@PathVariable String licensePlate){
        return vehicleService.findByLicensePlate(licensePlate);
    }
}
