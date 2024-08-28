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


/*@RestController
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
}*/









// after adding rest template






/*

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoanApplicationService loanApplicationService;

    @PostMapping
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody Customer customer) {
        try {
            Customer newCustomer = customerService.registerCustomer(customer);
            return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

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
        try {
            String loanOptions = customerService.getAvailableLoanOptions();
            return ResponseEntity.ok(loanOptions);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving loan options.");
        }
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<?> getLoanById(@PathVariable Long loanId) {
        try {
            Loan loan = customerService.getLoanById(loanId);
            if (loan != null) {
                return ResponseEntity.ok(loan);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while retrieving loan data.");
        }
    }

    @PostMapping("/{customerId}/loan-applications")
    public ResponseEntity<String> applyForLoan(
            @PathVariable Long customerId, 
            @Valid @RequestBody LoanApplicationRequest request) {
        Long loanId = request.getLoanId();
        try {
            loanApplicationService.applyForLoan(customerId, loanId);
            return ResponseEntity.ok("Loan application submitted successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while applying for the loan: " + e.getMessage());
        }
    }

    @GetMapping("/loan-options/type/{typeOfLoan}")
    public ResponseEntity<?> getLoansByType(@PathVariable("typeOfLoan") String typeOfLoan) {
        try {
            List<Loan> loans = customerService.getLoansByType(typeOfLoan);
            if (loans.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(loans);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid loan type: " + typeOfLoan);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving loans: " + e.getMessage());
        }
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
}
*/














@RestController
@RequestMapping("/customers")
@Validated
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoanApplicationService loanApplicationService;

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
    
    //it is by getApplicationByIdAndCustomerId
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
    
    
    //
    
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










