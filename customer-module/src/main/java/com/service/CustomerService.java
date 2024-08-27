package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Customer;
import com.repository.CustomerRepository;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    /*public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }*/    //this is my first code without adding exception
    
    
    //1. exception added for registration
    
    
    public Customer registerCustomer(Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists: " + customer.getEmail());
        }
        return customerRepository.save(customer);
    }
    
    
    
    /*public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }*/
    
    /*without exception
    public Customer login(String email, String password) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer != null && customer.getPassword().equals(password)) {
            return customer;
        }
        return null; // Return null or throw an exception if credentials are invalid
    }*/
    
    
    
    //2.exception added for login
    
    public Customer login(String email, String password) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        if (!customer.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return customer;
    }

    
    
  
    
    /*public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }*/
    
    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }
    
    
    
    
    
    
    
    
 // Add this method(newly added)
    public boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }
}
