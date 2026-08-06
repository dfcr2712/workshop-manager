package com.dfcr.workshopmanager.repository;

import com.dfcr.workshopmanager.entity.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {

   List<ServiceOrder> findByVehicleId(Long vehicleId);
}

