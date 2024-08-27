package com.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controller.LoanApplicationRequest;
import com.model.Customer;
import com.model.Loan;
import com.model.LoanApplication;
import com.repository.LoanApplicationRepository;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private LoanService loanService;

    @Autowired
    private CustomerService customerService;

   

    public Optional<LoanApplication> getApplicationById(Long applicationId) {
        return loanApplicationRepository.findById(applicationId);
    }
    
    
    
    public void applyForLoan(Long customerId, Long loanId) {
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setCustomerId(customerId);
        loanApplication.setLoanId(loanId);  // Set loanId directly

        // Save the application
        loanApplicationRepository.save(loanApplication);
    }

    

}