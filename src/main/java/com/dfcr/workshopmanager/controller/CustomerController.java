package com.dfcr.workshopmanager.controller;

import com.dfcr.workshopmanager.repository.CustomerRepository;
import com.dfcr.workshopmanager.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.dfcr.workshopmanager.entity.Customer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public Customer createCustomer(@RequestBody @Valid Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable long id, @RequestBody @Valid Customer updateCustomer) {

        return customerService.updateCustomer(id, updateCustomer);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }

    @GetMapping("/name/{name}")
    public List<Customer> findByName(@PathVariable String name) {
        return customerService.findByName(name);
    }

    @GetMapping("/email/{email}")
    public Customer findByEmail(@PathVariable String email) {
        return customerService.findByEmail(email);
    }
}
