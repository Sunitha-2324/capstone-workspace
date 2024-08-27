package com.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.model.Loan;
import com.model.LoanType;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Long> {
    List<Loan> findByTypeOfLoan(LoanType typeOfLoan);
    
    
    //trail
    
    //Optional<Loan> findById(Long loanId);
}
