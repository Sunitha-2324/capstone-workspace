package com.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Customer;
import com.model.Loan;
import com.model.LoanApplication;
import com.service.CustomerService;
import com.service.LoanApplicationService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoanApplicationService loanApplicationService;

    //1. Register for customer
    @PostMapping
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody Customer customer) {
        logger.info("Received request to register customer with email: {}", customer.getEmail());
        try {
            Customer newCustomer = customerService.registerCustomer(customer);
            logger.info("Customer registered successfully with email: {}", customer.getEmail());
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.warn("Registration failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred while registering customer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    
    //2. Customer Login 
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        logger.info("Received login request for email: {}", loginRequest.getEmail());
        try {
            Customer customer = customerService.login(loginRequest.getEmail(), loginRequest.getPassword());
            if (customer != null) {
                logger.info("Login successful for email: {}", loginRequest.getEmail());
                return ResponseEntity.ok("Login successful");
            } else {
                logger.warn("Login failed for email: {}. Invalid email or password.", loginRequest.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
        } catch (IllegalArgumentException e) {
            logger.warn("Login attempt failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred during login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    
    //3. It shows available loan options
    @GetMapping("/loan-options")
    public ResponseEntity<String> getAvailableLoanOptions() {
        logger.info("Received request to get available loan options");
        try {
            String loanOptions = customerService.getAvailableLoanOptions();
            return ResponseEntity.ok(loanOptions);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving loan options", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving loan options.");
        }
    }

    
    //4. search loan by ID
    @GetMapping("/loan/{loanId}")
    public ResponseEntity<?> getLoanById(@PathVariable Long loanId) {
        logger.info("Received request to get loan by ID: {}", loanId);
        try {
            Loan loan = customerService.getLoanById(loanId);
            if (loan != null) {
                return ResponseEntity.ok(loan);
            } else {
                logger.warn("Loan with ID {} not found", loanId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while retrieving loan data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving loan data.");
        }
    }
    
    

    //5. search loan application by using customer ID
    @PostMapping("/{customerId}/loan-applications")
    public ResponseEntity<String> applyForLoan(
            @PathVariable Long customerId, 
            @Valid @RequestBody LoanApplicationRequest request) {
        logger.info("Received loan application request for customer ID: {} with loan ID: {}", customerId, request.getLoanId());
        try {
            loanApplicationService.applyForLoan(customerId, request.getLoanId());
            return ResponseEntity.ok("Loan application submitted successfully.");
        } catch (IllegalArgumentException e) {
            logger.warn("Loan application failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred while applying for the loan", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while applying for the loan: " + e.getMessage());
        }
    }
    
    

    //6. It shows type of loans available in loan-options
    @GetMapping("/loan-options/type/{typeOfLoan}")
    public ResponseEntity<?> getLoansByType(@PathVariable("typeOfLoan") String typeOfLoan) {
        logger.info("Received request to get loans by type: {}", typeOfLoan);
        try {
            List<Loan> loans = customerService.getLoansByType(typeOfLoan);
            if (loans.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(loans);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid loan type: {}", typeOfLoan);
            return ResponseEntity.badRequest().body("Invalid loan type: " + typeOfLoan);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving loans", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    
    //it is getApplicationById
   /* @GetMapping("/application/{applicationId}")
    public ResponseEntity<?> getApplicationById(@PathVariable Long applicationId) {
        logger.info("Received request to get loan application by ID: {}", applicationId);
        if (applicationId == null || applicationId <= 0) {
            logger.warn("Invalid application ID provided: {}", applicationId);
            return ResponseEntity.badRequest().body("Invalid application ID provided.");
        }

        try {
            Optional<LoanApplication> application = loanApplicationService.getApplicationById(applicationId);
            if (application.isPresent()) {
                return ResponseEntity.ok(application.get());
            } else {
                logger.warn("Loan application with ID {} not found", applicationId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Loan application with ID " + applicationId + " not found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while retrieving loan application data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An error occurred: " + e.getMessage());
        }
    }*/
    
    //7. it will shows the application details by using customerId and ApplicationId
    @GetMapping("/{customerId}/application/{applicationId}")
    public ResponseEntity<?> getApplicationByIdAndCustomerId(
        @PathVariable Long customerId,
        @PathVariable Long applicationId
    ) {
        logger.info("Received request to get loan application by customer ID: {} and application ID: {}", customerId, applicationId);

        if (customerId == null || customerId <= 0 || applicationId == null || applicationId <= 0) {
            logger.warn("Invalid customer ID or application ID provided: customerId={}, applicationId={}", customerId, applicationId);
            return ResponseEntity.badRequest().body("Invalid customer ID or application ID provided.");
        }

        try {
            Optional<LoanApplication> application = loanApplicationService.getApplicationByCustomerIdAndApplicationId(customerId, applicationId);
            if (application.isPresent()) {
                return ResponseEntity.ok(application.get());
            } else {
                logger.warn("Loan application with customer ID {} and application ID {} not found", customerId, applicationId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Loan application with customer ID " + customerId + " and application ID " + applicationId + " not found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while retrieving loan application data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An error occurred: " + e.getMessage());
        }
    }
    
    
    
    
    //8. it shows application details by using customerId
    
    @GetMapping("/{customerId}/applications")
    public ResponseEntity<?> getApplicationsByCustomerId(@PathVariable Long customerId) {
        logger.info("Received request to get loan applications by customer ID: {}", customerId);

        if (customerId == null || customerId <= 0) {
            logger.warn("Invalid customer ID provided: {}", customerId);
            return ResponseEntity.badRequest().body("Invalid customer ID provided.");
        }

        try {
            List<LoanApplication> applications = loanApplicationService.getApplicationsByCustomerId(customerId);
            if (!applications.isEmpty()) {
                return ResponseEntity.ok(applications);
            } else {
                logger.warn("No loan applications found for customer ID {}", customerId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("No loan applications found for customer ID " + customerId + ".");
            }
        } catch (Exception e) {
            logger.error("An error occurred while retrieving loan application data", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An error occurred: " + e.getMessage());
        }
    }


}










