package com.dfcr.workshopmanager.controller;

import com.dfcr.workshopmanager.service.CustomerService;
import org.springframework.web.bind.annotation.*;
import com.dfcr.workshopmanager.entity.Customer;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer){
        return customerService.saveCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

}
