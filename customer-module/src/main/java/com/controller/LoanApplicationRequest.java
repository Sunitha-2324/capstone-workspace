package com.controller;

public class LoanApplicationRequest {

	
    private Long loanId;

    // Getters and setters

   
    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long loanId) {
        this.loanId = loanId;
    }

	

	public LoanApplicationRequest(Long customerId, Long loanId) {
		super();
		
		this.loanId = loanId;
	}

	public LoanApplicationRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}

