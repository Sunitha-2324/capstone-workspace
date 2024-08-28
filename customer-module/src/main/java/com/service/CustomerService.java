package com.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.model.Customer;
import com.model.Loan;
import com.repository.CustomerRepository;

/*@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    
    
    //1. exception added for registration
    
    
    public Customer registerCustomer(Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists: " + customer.getEmail());
        }
        return customerRepository.save(customer);
    }
    
    
    
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

    
    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }
    
   
 // Add this method(newly added)
    public boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }
}*/







/*
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String LOAN_SERVICE_URL = "http://loanms/loans";

    public Customer registerCustomer(Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists: " + customer.getEmail());
        }
        return customerRepository.save(customer);
    }

    public Customer login(String email, String password) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null || !customer.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        return customer;
    }

    public String getAvailableLoanOptions() {
        return restTemplate.getForObject(LOAN_SERVICE_URL, String.class);
    }

    public Loan getLoanById(Long loanId) {
        String url = LOAN_SERVICE_URL + "/" + loanId;
        ResponseEntity<Loan> response = restTemplate.getForEntity(url, Loan.class);
        return response.getBody();
    }

    public List<Loan> getLoansByType(String typeOfLoan) {
        String url = LOAN_SERVICE_URL + "/type/" + typeOfLoan;
        ResponseEntity<List<Loan>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Loan>>() {}
        );
        return response.getBody();
    }

    public boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }
}*/





@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String LOAN_SERVICE_URL = "http://loanms/loans";

    public Customer registerCustomer(Customer customer) {
        logger.info("Attempting to register customer with email: {}", customer.getEmail());
        if (customerRepository.findByEmail(customer.getEmail()) != null) {
            logger.warn("Registration failed: Email already exists: {}", customer.getEmail());
            throw new IllegalArgumentException("Email already exists: " + customer.getEmail());
        }
        Customer savedCustomer = customerRepository.save(customer);
        logger.info("Customer registered successfully with email: {}", customer.getEmail());
        return savedCustomer;
    }

    public Customer login(String email, String password) {
        logger.info("Attempting to log in customer with email: {}", email);
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null || !customer.getPassword().equals(password)) {
            logger.warn("Login failed for email: {}. Invalid email or password.", email);
            throw new IllegalArgumentException("Invalid email or password");
        }
        logger.info("Customer logged in successfully with email: {}", email);
        return customer;
    }

    public String getAvailableLoanOptions() {
        logger.info("Fetching available loan options from loan service.");
        String loanOptions = restTemplate.getForObject(LOAN_SERVICE_URL, String.class);
        logger.debug("Available loan options: {}", loanOptions);
        return loanOptions;
    }

    public Loan getLoanById(Long loanId) {
        logger.info("Fetching loan details for loan ID: {}", loanId);
        String url = LOAN_SERVICE_URL + "/" + loanId;
        ResponseEntity<Loan> response;
        try {
            response = restTemplate.getForEntity(url, Loan.class);
        } catch (Exception e) {
            logger.error("Error fetching loan details for loan ID: {}", loanId, e);
            throw e; 
        }
        Loan loan = response.getBody();
        if (loan == null) {
            logger.warn("Loan with ID {} not found", loanId);
        } else {
            logger.info("Fetched loan details for loan ID: {}", loanId);
        }
        return loan;
    }

    public List<Loan> getLoansByType(String typeOfLoan) {
        logger.info("Fetching loans by type: {}", typeOfLoan);
        String url = LOAN_SERVICE_URL + "/type/" + typeOfLoan;
        ResponseEntity<List<Loan>> response;
        try {
            response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Loan>>() {}
            );
        } catch (Exception e) {
            logger.error("Error fetching loans by type: {}", typeOfLoan, e);
            throw e;  
        }
        List<Loan> loans = response.getBody();
        if (loans == null || loans.isEmpty()) {
            logger.warn("No loans found for type: {}", typeOfLoan);
        } else {
            logger.info("Fetched {} loans for type: {}", loans.size(), typeOfLoan);
        }
        return loans;
    }

    public boolean existsById(Long id) {
        logger.debug("Checking if customer with ID {} exists", id);
        boolean exists = customerRepository.existsById(id);
        logger.info("Customer with ID {} exists: {}", id, exists);
        return exists;
    }
}

