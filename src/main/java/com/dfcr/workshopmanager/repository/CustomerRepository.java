package com.dfcr.workshopmanager.repository;

import com.dfcr.workshopmanager.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByNameContainingIgnoreCase(String name);

    Optional<Customer> findByEmail(String email);
}
