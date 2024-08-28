package com.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.model.Loan;
import com.model.LoanApplication;
import com.repository.LoanApplicationRepository;
/*
@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RestTemplate restTemplate;

    private static final String LOAN_SERVICE_URL = "http://loanms/loans";

    public Optional<LoanApplication> getApplicationById(Long applicationId) {
        return loanApplicationRepository.findById(applicationId);
    }

    public void applyForLoan(Long customerId, Long loanId) {
        // Retrieve loan details from the remote Loan service
        Loan loan = restTemplate.getForObject(LOAN_SERVICE_URL + "/" + loanId, Loan.class);

        if (loan == null) {
            throw new IllegalArgumentException("Loan with ID " + loanId + " not found.");
        }

        // Check if the customer exists
        if (!customerService.existsById(customerId)) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist.");
        }

        // Create and save the loan application
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setCustomerId(customerId);
        loanApplication.setLoanId(loanId);

        loanApplicationRepository.save(loanApplication);
    }
}
*/


@Service
public class LoanApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationService.class);

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RestTemplate restTemplate;

    private static final String LOAN_SERVICE_URL = "http://loanms/loans";
  
    
    /*(getApplicationById)
    public Optional<LoanApplication> getApplicationById(Long applicationId) {
        logger.info("Fetching loan application with ID: {}", applicationId);
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findById(applicationId);
        if (loanApplication.isPresent()) {
            logger.info("Loan application found: {}", loanApplication.get());
        } else {
            logger.warn("Loan application with ID {} not found", applicationId);
        }
        return loanApplication;
    }*/
    
    
    
    //(getApplicationByCustomerIdAndApplicationId)
    public Optional<LoanApplication> getApplicationByCustomerIdAndApplicationId(Long customerId, Long applicationId) {
        logger.info("Fetching loan application with customer ID: {} and application ID: {}", customerId, applicationId);
        Optional<LoanApplication> loanApplication = loanApplicationRepository.findByCustomerIdAndApplicationId(customerId, applicationId);
        if (loanApplication.isPresent()) {
            logger.info("Loan application found: {}", loanApplication.get());
        } else {
            logger.warn("Loan application with customer ID {} and application ID {} not found", customerId, applicationId);
        }
        return loanApplication;
    }
    
    //3. getApplicationsByCustomerId
    public List<LoanApplication> getApplicationsByCustomerId(Long customerId) {
        logger.info("Fetching loan applications with customer ID: {}", customerId);
        List<LoanApplication> loanApplications = loanApplicationRepository.findByCustomerId(customerId);
        if (!loanApplications.isEmpty()) {
            logger.info("Loan applications found: {}", loanApplications);
        } else {
            logger.warn("No loan applications found for customer ID {}", customerId);
        }
        return loanApplications;
    }

    
    //apply for loan
    
    public void applyForLoan(Long customerId, Long loanId) {
        logger.info("Processing loan application. Customer ID: {}, Loan ID: {}", customerId, loanId);

        Loan loan;
        try {
            loan = restTemplate.getForObject(LOAN_SERVICE_URL + "/" + loanId, Loan.class);
        } catch (Exception e) {
            logger.error("Error retrieving loan details from the loan service for Loan ID: {}", loanId, e);
            throw new IllegalArgumentException("Error retrieving loan details from the service: " + e.getMessage(), e);
        }

        if (loan == null) {
            logger.warn("Loan with ID {} not found", loanId);
            throw new IllegalArgumentException("Loan with ID " + loanId + " not found.");
        }

        if (!customerService.existsById(customerId)) {
            logger.warn("Customer with ID {} does not exist", customerId);
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist.");
        }

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setCustomerId(customerId);
        loanApplication.setLoanId(loanId);

        try {
            loanApplicationRepository.save(loanApplication);
            logger.info("Loan application saved successfully for Customer ID: {} and Loan ID: {}", customerId, loanId);
        } catch (Exception e) {
            logger.error("Error saving loan application for Customer ID: {} and Loan ID: {}", customerId, loanId, e);
            throw e;
        }
    }
}





