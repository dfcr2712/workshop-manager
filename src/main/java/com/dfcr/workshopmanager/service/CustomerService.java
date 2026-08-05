package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.Customer;
import com.dfcr.workshopmanager.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }


}
