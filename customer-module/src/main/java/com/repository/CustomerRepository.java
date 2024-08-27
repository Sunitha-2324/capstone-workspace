package com.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByEmail(String email);
    
    
    
    
    //extra one newly added method
    
    
    //boolean existsById(Long id);
    
    
    //trail
    
    //Optional<Customer> findById(Long customerId);
}


