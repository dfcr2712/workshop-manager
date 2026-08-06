package com.dfcr.workshopmanager.repository;

import com.dfcr.workshopmanager.entity.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {


}
