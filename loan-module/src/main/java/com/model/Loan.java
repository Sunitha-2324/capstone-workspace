package com.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "loanoptions")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Long loanId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_loan", nullable = false)
    @NotNull(message = "Type of loan cannot be null")
    private LoanType typeOfLoan;

    
    @Column(name = "interest_rate", nullable = false)
    private BigDecimal interestRate;

    @Column(name = "max_amount", nullable = false)
    private BigDecimal maxAmount;

    @Column(name = "min_amount", nullable = false)
    private BigDecimal minAmount;

    @Column(name = "term_in_months", nullable = false)
    @Min(value = 5, message = "Term in months must be at least 1")
    @Max(value = 360, message = "Term in months must be no more than 360")
    private int termInMonths;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public LoanType getTypeOfLoan() {
		return typeOfLoan;
	}

	public void setTypeOfLoan(LoanType typeOfLoan) {
		this.typeOfLoan = typeOfLoan;
	}

	public BigDecimal getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(BigDecimal interestRate) {
		this.interestRate = interestRate;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public int getTermInMonths() {
		return termInMonths;
	}

	public void setTermInMonths(int termInMonths) {
		this.termInMonths = termInMonths;
	}

	public Loan(Long loanId, LoanType typeOfLoan, BigDecimal interestRate, BigDecimal maxAmount, BigDecimal minAmount,
			int termInMonths) {
		super();
		this.loanId = loanId;
		this.typeOfLoan = typeOfLoan;
		this.interestRate = interestRate;
		this.maxAmount = maxAmount;
		this.minAmount = minAmount;
		this.termInMonths = termInMonths;
	}

	public Loan() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
	/*@Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", typeOfLoan=" + typeOfLoan +
                ", interestRate=" + interestRate +
                ", maxAmount=" + maxAmount +
                ", minAmount=" + minAmount +
                ", termInMonths=" + termInMonths +
                '}';
    }*/
    
}
