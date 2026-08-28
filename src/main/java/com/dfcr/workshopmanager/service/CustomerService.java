package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.Customer;
import com.dfcr.workshopmanager.entity.Vehicle;
import com.dfcr.workshopmanager.exception.CustomerNotFoundException;
import com.dfcr.workshopmanager.repository.CustomerRepository;
import com.dfcr.workshopmanager.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final VehicleRepository vehicleRepository;

    public CustomerService(CustomerRepository customerRepository, VehicleRepository vehicleRepository) {
        this.customerRepository = customerRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existingCustomer = getCustomerById(id);

        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setNif(updatedCustomer.getNif());
        existingCustomer.setAddress(updatedCustomer.getAddress());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());

        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(long id) {
        Customer customer = getCustomerById(id);
        List<Vehicle> existingVehicles = vehicleRepository.findByCustomerId(id);
        if (!existingVehicles.isEmpty()) {
            throw new IllegalArgumentException("Customer with id " + id + " cannot be deleted because it has vehicles.");
        }

        customerRepository.delete(customer);
    }

    public List<Customer> findByName(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name);
    }

    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email).orElseThrow(() -> new CustomerNotFoundException(email));
    }
}
