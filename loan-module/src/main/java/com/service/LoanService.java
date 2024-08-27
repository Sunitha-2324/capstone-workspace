package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Loan;
import com.model.LoanType;
import com.repository.LoanRepository;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public List<Loan> getAllLoans() {
        return (List<Loan>) loanRepository.findAll();
    }

    public Optional<Loan> getLoanById(Long loanId) {
        return loanRepository.findById(loanId);
    }

    public List<Loan> getLoansByType(LoanType typeOfLoan) {
        return loanRepository.findByTypeOfLoan(typeOfLoan);
    }
}


