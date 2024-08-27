package com.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "loanapplication")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    @NotNull(message = "Customer ID cannot be null")
    @Min(value = 1, message = "Customer ID must be greater than 0")
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @NotNull(message = "Loan ID cannot be null")
    @Min(value = 1, message = "Loan ID must be greater than 0")
    @Column(name = "loan_id", nullable = false)
    private Long loanId;

    @NotNull(message = "Application date cannot be null")
    @Column(name = "application_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date applicationDate;

    @NotNull(message = "Status cannot be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    // Constructors, Getters, and Setters

    public LoanApplication() {
        this.applicationDate = new Date(); // Set default application date
        this.status = ApplicationStatus.PENDING; // Set default status
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public enum ApplicationStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
} 






/*@Entity
@Table(name = "loanapplication")
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "loan_id")
    private Long loanId;

    @Column(name = "application_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date applicationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    public LoanApplication() {
        this.applicationDate = new Date(); // Set default application date
        this.status = ApplicationStatus.PENDING; // Set default status
    }

    // Getters and Setters

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    

    public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
    
    

    public LoanApplication(Long applicationId, Customer customer, Long loanId, Date applicationDate,
			ApplicationStatus status) {
		super();
		this.applicationId = applicationId;
		this.customer = customer;
		this.loanId = loanId;
		this.applicationDate = applicationDate;
		this.status = status;
	}

    

	public enum ApplicationStatus {
        PENDING,
        APPROVED,
        REJECTED
    }
}*/