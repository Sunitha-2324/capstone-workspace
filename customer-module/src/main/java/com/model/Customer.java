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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;
    
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    @Column(nullable = false)
    private String password;
    
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;
    
    @NotBlank(message = "Customer name cannot be empty")
    @Column(name = "customer_name", nullable = false)
    private String customerName;
    
    @Pattern(regexp = "\\d{10}", message = "Phone number should be 10 digits")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Size(max = 255, message = "Address cannot be longer than 255 characters")
    private String address;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender cannot be null")
    @Column(nullable = false)
    private Gender gender;
 
    @Past(message = "Date of birth must be in the past")
    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @NotBlank(message = "PAN card cannot be empty")
    @Pattern(regexp = "[A-Z]{5}\\d{4}[A-Z]{1}", message = "PAN card number should be in Correct format")
    @Column(name = "pan_card", unique = true, nullable = false)
    private String panCard;

    // Getters and setters

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }
    
    

    public Customer(Long customerId, String password, String email, String customerName, String phoneNumber,
			String address, Gender gender, Date dateOfBirth, String panCard) {
		super();
		this.customerId = customerId;
		this.password = password;
		this.email = email;
		this.customerName = customerName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.panCard = panCard;
	}

    
    
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", customerName='" + customerName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", panCard='" + panCard + '\'' +
                '}';
    }
	public enum Gender {
		MALE,
	    FEMALE,
	    OTHER

	}
}
