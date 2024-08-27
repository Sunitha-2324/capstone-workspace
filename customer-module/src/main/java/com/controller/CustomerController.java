package com.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.exceptions.CustomException;
import com.model.Customer;
import com.model.Loan;
import com.model.LoanApplication;
import com.service.CustomerService;
import com.service.LoanApplicationService;
import com.service.LoanService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    
    //extra added
    @Autowired
    private LoanApplicationService loanApplicationService;
    
    
    @Autowired
    private RestTemplate restTemplate;

    // URL using the service name registered in Eureka
    private static final String LOAN_SERVICE_URL = "http://loanms/loans";
    
    
    
    
    //1. method one exception added for registration
    
    @PostMapping
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody Customer customer) {
        try {
            Customer newCustomer = customerService.registerCustomer(customer);
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Handle specific exception for duplicate email or invalid input
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Handle any other exceptions
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
    
    
    
    
    
    
    //login another
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Customer customer = customerService.login(loginRequest.getEmail(), loginRequest.getPassword());
            if (customer != null) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }


    
    
    
    
    
    
    
    
    @GetMapping("/loan-options")
    public ResponseEntity<String> getAvailableLoanOptions() {
        String loanOptions = restTemplate.getForObject(LOAN_SERVICE_URL, String.class);
        return ResponseEntity.ok(loanOptions);
    }
    
    
    
    
    
    @GetMapping("/application/{applicationId}")
    public ResponseEntity<?> getApplicationById(@PathVariable Long applicationId) {
        if (applicationId == null || applicationId <= 0) {
            return ResponseEntity.badRequest().body("Invalid application ID provided.");
        }

        try {
            Optional<LoanApplication> application = loanApplicationService.getApplicationById(applicationId);
            if (application.isPresent()) {
                return ResponseEntity.ok(application.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                     .body("Loan application with ID " + applicationId + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An error occurred: " + e.getMessage());
        }
    }

    
    
    
    
    
 
    
    
    @PostMapping("/{customerId}/loan-applications")
    public ResponseEntity<String> applyForLoan(
            @PathVariable Long customerId, 
            @Valid @RequestBody LoanApplicationRequest request) {
        Long loanId = request.getLoanId();

        
        String loanUrl = LOAN_SERVICE_URL + "/" + loanId;
        System.out.println("Attempting to retrieve loan details from URL: " + loanUrl);

        try {
            ResponseEntity<Loan> response = restTemplate.getForEntity(loanUrl, Loan.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                loanApplicationService.applyForLoan(customerId, loanId);
                return ResponseEntity.ok("Loan application submitted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan with ID " + loanId + " not found.");
            }
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan with ID " + loanId + " not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while applying for the loan: " + e.getMessage());
        }
    }

    
    

    
    
    
    @GetMapping("/loan/{loanId}")
    public ResponseEntity<?> getLoanById(@PathVariable Long loanId) {
        String url = LOAN_SERVICE_URL + "/" + loanId;
        try {
            ResponseEntity<Loan> response = restTemplate.getForEntity(url, Loan.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the loan data.");
        }
    }
    
    
    @GetMapping("/loan-options/type/{typeOfLoan}")
    public ResponseEntity<?> getLoansByType(@PathVariable("typeOfLoan") String typeOfLoan) {
        String url = LOAN_SERVICE_URL + "/type/" + typeOfLoan;

        try {
            // Fetch loans by type from the LoanService
            ResponseEntity<List<Loan>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Loan>>() {}
            );

            if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException.BadRequest e) {
            return ResponseEntity.badRequest().body("Invalid loan type: " + typeOfLoan);
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loans not found for type: " + typeOfLoan);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving loans: " + e.getMessage());
        }
    }
}



















