package com.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.model.Loan;
import com.model.LoanType;
import com.service.LoanService;

/*
@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

   
    
    @GetMapping
    public ResponseEntity<?> getAllLoans() {
        try {
            List<Loan> loans = ((LoanService) loanService).getAllLoans();
            if (loans.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the loan data.");
        }
    }

   
    
    @GetMapping("/{loanId}")
    public ResponseEntity<?> getLoanById(@PathVariable Long loanId) {
        try {
            Optional<Loan> loan = loanService.getLoanById(loanId);
            if (loan.isPresent()) {
                return ResponseEntity.ok(loan.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching the loan data.");
        }
    }
    
    
    
    @GetMapping("/type/{typeOfLoan}")
    public ResponseEntity<?> getLoansByType(@PathVariable("typeOfLoan") String typeOfLoan) {
        LoanType loanType;
        try {
            loanType = LoanType.valueOf(typeOfLoan.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid loan type: " + typeOfLoan);
        }

        try {
            List<Loan> loans = loanService.getLoansByType(loanType);
            if (loans.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving loans: " + e.getMessage());
        }
    }

}*/




@RestController
@RequestMapping("/loans")
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    @Autowired
    private LoanService loanService;

    @GetMapping
    public ResponseEntity<?> getAllLoans() {
        logger.debug("Received request to get all loans");
        try {
            List<Loan> loans = loanService.getAllLoans();
            if (loans.isEmpty()) {
                logger.info("No loans found");
                return ResponseEntity.noContent().build();
            }
            logger.info("Retrieved {} loans", loans.size());
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            logger.error("An error occurred while fetching all loans", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the loan data.");
        }
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<?> getLoanById(@PathVariable Long loanId) {
        logger.debug("Received request to get loan with ID: {}", loanId);
        try {
            Optional<Loan> loan = loanService.getLoanById(loanId);
            if (loan.isPresent()) {
                logger.info("Found loan with ID: {}", loanId);
                return ResponseEntity.ok(loan.get());
            } else {
                logger.warn("Loan with ID: {} not found", loanId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan not found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while fetching loan with ID: {}", loanId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while fetching the loan data.");
        }
    }

    @GetMapping("/type/{typeOfLoan}")
    public ResponseEntity<?> getLoansByType(@PathVariable("typeOfLoan") String typeOfLoan) {
        LoanType loanType;
        logger.debug("Received request to get loans by type: {}", typeOfLoan);
        try {
            loanType = LoanType.valueOf(typeOfLoan.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid loan type: {}", typeOfLoan);
            return ResponseEntity.badRequest().body("Invalid loan type: " + typeOfLoan);
        }

        try {
            List<Loan> loans = loanService.getLoansByType(loanType);
            if (loans.isEmpty()) {
                logger.info("No loans found for type: {}", loanType);
                return ResponseEntity.noContent().build();
            }
            logger.info("Retrieved {} loans for type: {}", loans.size(), loanType);
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            logger.error("An error occurred while retrieving loans of type: {}", loanType, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while retrieving loans: " + e.getMessage());
        }
    }
}
