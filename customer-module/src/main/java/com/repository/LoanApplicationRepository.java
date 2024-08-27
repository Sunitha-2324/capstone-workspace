package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.LoanApplication;

@Repository
public interface LoanApplicationRepository extends CrudRepository<LoanApplication, Long> {
    // Custom query methods (if needed) can be defined here
}
