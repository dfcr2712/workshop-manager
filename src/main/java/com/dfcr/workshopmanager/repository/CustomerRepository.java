package com.dfcr.workshopmanager.repository;

import com.dfcr.workshopmanager.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
