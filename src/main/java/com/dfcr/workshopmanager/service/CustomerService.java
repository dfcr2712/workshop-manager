package com.dfcr.workshopmanager.service;

import com.dfcr.workshopmanager.entity.Customer;
import com.dfcr.workshopmanager.exception.CustomerNotFoundException;
import com.dfcr.workshopmanager.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer){
        Customer existingCustomer = getCustomerById(id);

        existingCustomer.setName(updatedCustomer.getName());
        existingCustomer.setNif(updatedCustomer.getNif());
        existingCustomer.setAddress(updatedCustomer.getAddress());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());

        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(long id){
        Customer c = getCustomerById(id);
        customerRepository.delete(c);
    }
}
