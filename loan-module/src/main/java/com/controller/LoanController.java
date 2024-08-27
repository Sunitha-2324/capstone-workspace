package com.controller;

import java.util.List;
import java.util.Optional;

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

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

   
    
    @GetMapping
    public ResponseEntity<?> getAllLoans() {
        try {
            List<Loan> loans = loanService.getAllLoans();
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

}
