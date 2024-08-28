package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.LoanApplication;

@Repository
public interface LoanApplicationRepository extends CrudRepository<LoanApplication, Long> {
    // Custom query methods (if needed) can be defined here
	Optional<LoanApplication> findById(Long applicationId);
	
	Optional<LoanApplication> findByCustomerIdAndApplicationId(Long customerId, Long applicationId);
	
	List<LoanApplication> findByCustomerId(Long customerId);
}
