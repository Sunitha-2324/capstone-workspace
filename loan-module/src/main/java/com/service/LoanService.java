package com.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.Loan;
import com.model.LoanType;
import com.repository.LoanRepository;

/*
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
}*/


@Service
public class LoanService {

    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

    @Autowired
    private LoanRepository loanRepository;

    public List<Loan> getAllLoans() {
        logger.debug("Fetching all loans");
        List<Loan> loans = (List<Loan>) loanRepository.findAll();
        logger.info("Retrieved {} loans", loans.size());
        return loans;
    }

    public Optional<Loan> getLoanById(Long loanId) {
        logger.debug("Fetching loan with ID: {}", loanId);
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isPresent()) {
            logger.info("Found loan with ID: {}", loanId);
        } else {
            logger.warn("No loan found with ID: {}", loanId);
        }
        return loan;
    }

    public List<Loan> getLoansByType(LoanType typeOfLoan) {
        logger.debug("Fetching loans of type: {}", typeOfLoan);
        List<Loan> loans = loanRepository.findByTypeOfLoan(typeOfLoan);
        logger.info("Retrieved {} loans of type: {}", loans.size(), typeOfLoan);
        return loans;
    }
}








