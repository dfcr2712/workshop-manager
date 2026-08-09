package com.dfcr.workshopmanager.repository;

import com.dfcr.workshopmanager.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByCustomerId(Long costumerId);
    Optional<Vehicle> findByLicensePlate(String licensePlate);




}
